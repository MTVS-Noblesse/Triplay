package com.noblesse.backend.post.api.command;

import com.noblesse.backend.post.command.application.handler.*;
import com.noblesse.backend.post.common.dto.PostReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/postReport")
@RequiredArgsConstructor
@Tag(name = "Post Report Command Controller")
public class PostReportCommandController {

    private final CreatePostReportCommandHandler createPostReportCommandHandler;
    private final UpdatePostReportCommandHandler updatePostReportCommandHandler;
    private final DeletePostReportCommandHandler deletePostReportCommandHandler;

    @Operation(summary = "포스트 신고글 추가")
    @PostMapping
    public ResponseEntity<Long> createPostReport(@RequestBody PostReportDTO command) {
        Long postReportId = createPostReportCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(postReportId);
    }

    @Operation(summary = "포스트 신고글 수정")
    @PutMapping("/{postReportId}")
    public ResponseEntity<Void> updatePostReport(
            @PathVariable("postReportId") Long postReportId,
            @RequestBody PostReportDTO command
    ) {
        command.setPostReportId(postReportId);
        updatePostReportCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "포스트 신고글 삭제")
    @DeleteMapping("/{postReportId}")
    public ResponseEntity<Void> deletePostReport(
            @PathVariable("postReportId") Long postReportId,
            @RequestBody PostReportDTO command
    ) {
        command.setPostReportId(postReportId);
        deletePostReportCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}
