package com.noblesse.backend.post.api.command;

import com.noblesse.backend.post.command.application.dto.CreatePostCommandDTO;
import com.noblesse.backend.post.command.application.dto.DeletePostCommandDTO;
import com.noblesse.backend.post.command.application.dto.UpdatePostCommandDTO;
import com.noblesse.backend.post.command.application.handler.CreatePostCommandHandler;
import com.noblesse.backend.post.command.application.handler.DeletePostCommandHandler;
import com.noblesse.backend.post.command.application.handler.UpdatePostCommandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post Command Controller")
public class PostCommandController {

    private final CreatePostCommandHandler createPostCommandHandler;
    private final UpdatePostCommandHandler updatePostCommandHandler;
    private final DeletePostCommandHandler deletePostCommandHandler;

    @Operation(summary = "게시물 생성")
    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody CreatePostCommandDTO command) {
        Long postId = createPostCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(postId);
    }

    @Operation(summary = "게시물 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody UpdatePostCommandDTO command) {
        command.setPostId(id);
        updatePostCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "게시물 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestParam Long userId) {
        DeletePostCommandDTO command = new DeletePostCommandDTO(id, userId);
        deletePostCommandHandler.handle(command);
        return ResponseEntity.noContent().build();
    }
}
