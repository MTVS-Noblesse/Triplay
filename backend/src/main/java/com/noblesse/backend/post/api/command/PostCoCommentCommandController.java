package com.noblesse.backend.post.api.command;

import com.noblesse.backend.post.command.application.handler.*;
import com.noblesse.backend.post.common.dto.PostCoCommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/postCoComment")
@RequiredArgsConstructor
@Tag(name = "Post Co Comment Command Controller")
public class PostCoCommentCommandController {

    private final CreatePostCoCommentCommandHandler createPostCoCommentCommandHandler;
    private final UpdatePostCoCommentCommandHandler updatePostCoCommentCommandHandler;
    private final DeletePostCoCommentCommandHandler deletePostCoCommentCommandHandler;

    @Operation(summary = "포스트 대댓글 추가")
    @PostMapping
    public ResponseEntity<Long> createPostCoComment(
            @RequestBody PostCoCommentDTO command
    ) {
        Long postCoCommentId = createPostCoCommentCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCoCommentId);
    }

    @Operation(summary = "포스트 대댓글 수정")
    @PutMapping("/{postCoCommentId}")
    public ResponseEntity<Void> updatePostCoComment(
            @PathVariable("postCoCommentId") Long postCoCommentId,
            @RequestBody PostCoCommentDTO command
    ) {
        command.setPostCoCommentId(postCoCommentId);
        updatePostCoCommentCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "포스트 대댓글 삭제")
    @DeleteMapping("/{postCoCommentId}")
    public ResponseEntity<Void> deletePostCoComment(
            @PathVariable("postCoCommentId") Long postCoCommentId,
            @RequestBody PostCoCommentDTO command
    ) {
        command.setPostCoCommentId(postCoCommentId);
        deletePostCoCommentCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}
