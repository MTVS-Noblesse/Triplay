package com.noblesse.backend.post.api.query;

import com.noblesse.backend.post.common.dto.PostCommentDTO;
import com.noblesse.backend.post.query.application.service.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/posts/comment")
@RequiredArgsConstructor
@Tag(name = "Post Comment Query Controller")
public class PostCommentQueryController {

    private final PostQueryService postQueryService;

    @Operation(summary = "게시물 댓글 ID로 게시물 댓글 조회")
    @GetMapping(value = "/{postCommentId}")
    public ResponseEntity<PostCommentDTO> getPostCommentById(
            @PathVariable("postCommentId") Long postCommentId
    ) {
        PostCommentDTO postComment = postQueryService.getPostCommentById(postCommentId);

        return ResponseEntity.ok(postComment);
    }

    @Operation(summary = "게시물 댓글 목록 조회")
    @GetMapping
    public ResponseEntity<List<PostCommentDTO>> getPostComments() {
        List<PostCommentDTO> postComments = postQueryService.getAllPostComments();

        return ResponseEntity.ok(postComments);
    }
    
    @Operation(summary = "특정 사용자의 게시물의 모든 댓글 조회")
    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<List<PostCommentDTO>> getPostCommentsByUserId(
            @PathVariable Long userId
    ) {
        List<PostCommentDTO> postComments = postQueryService.getPostCommentsByUserId(userId);

        return ResponseEntity.ok(postComments);
    }
}
