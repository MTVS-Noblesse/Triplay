package com.noblesse.backend.post.api.command;

import com.noblesse.backend.post.command.application.handler.*;
import com.noblesse.backend.post.common.dto.PostCommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/postComment")
@RequiredArgsConstructor
@Tag(name = "Post Comment Command Controller")
public class PostCommentCommandController {

    private final CreatePostCommentCommandHandler createPostCommentCommandHandler;
    private final UpdatePostCommentCommandHandler updatePostCommentCommandHandler;
    private final DeletePostCommentCommandHandler deletePostCommentCommandHandler;

    @Operation(summary = "포스트 댓글 추가")
    @PostMapping
    public ResponseEntity<Long> createPostComment(
            @RequestBody PostCommentDTO command
    ) {
        Long postCommentId = createPostCommentCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCommentId);
    }

    @Operation(summary = "포스트 댓글 수정")
    @PutMapping("/{postCommentId}")
    public ResponseEntity<Void> updatePostComment(
            @PathVariable("postCommentId") Long postCommentId,
            @RequestBody PostCommentDTO command
    ) {
        command.setPostCommentId(postCommentId);
        updatePostCommentCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "포스트 댓글 삭제")
    @DeleteMapping("/{postCommentId}")
    public ResponseEntity<Void> deletePostComment(
            @PathVariable("postCommentId") Long postCommentId,
            @RequestBody PostCommentDTO command
    ) {
        command.setPostCommentId(postCommentId);
        deletePostCommentCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}
