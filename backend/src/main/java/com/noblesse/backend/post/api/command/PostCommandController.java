package com.noblesse.backend.post.api.command;

import com.noblesse.backend.post.command.application.handler.CreatePostCommandHandler;
import com.noblesse.backend.post.command.application.handler.DeletePostCommandHandler;
import com.noblesse.backend.post.command.application.handler.UpdatePostCommandHandler;
import com.noblesse.backend.post.common.dto.PostDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/post")
@RequiredArgsConstructor
@Tag(name = "Post Command Controller")
public class PostCommandController {

    private final CreatePostCommandHandler createPostCommandHandler;
    private final UpdatePostCommandHandler updatePostCommandHandler;
    private final DeletePostCommandHandler deletePostCommandHandler;

    @Operation(summary = "포스트 추가")
    @PostMapping
    public ResponseEntity<Long> createPost(
            @RequestBody PostDTO command
    ) {
        Long postId = createPostCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    @Operation(summary = "포스트 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostDTO command
    ) {
        command.setPostId(postId);
        updatePostCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "포스트 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable("postId") Long postId,
            @RequestParam Long userId
    ) {
        PostDTO command = new PostDTO(postId, userId);
        deletePostCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}
