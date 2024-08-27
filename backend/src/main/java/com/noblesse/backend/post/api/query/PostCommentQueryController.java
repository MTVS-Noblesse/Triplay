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
@RequestMapping(value = "/postComment")
@RequiredArgsConstructor
@Tag(name = "Post Comment Query Controller")
public class PostCommentQueryController {

    private final PostQueryService postQueryService;

    @Operation(summary = "포스트 댓글 상세 조회")
    @GetMapping("/{postCommentId}")
    public ResponseEntity<PostCommentDTO> getPostCommentById(@PathVariable("postCommentId") Long postCommentId) {
        PostCommentDTO postComment = postQueryService.getPostCommentById(postCommentId);
        return ResponseEntity.ok(postComment);
    }

    @Operation(summary = "포스트에 따른 댓글 전체 조회")
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostCommentDTO>> getPostCommentsByPostId(@PathVariable("postId") Long postId) {
        List<PostCommentDTO> postComments = postQueryService.getPostCommentsByPostId(postId);
        return ResponseEntity.ok(postComments);
    }
}
