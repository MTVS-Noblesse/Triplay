package com.noblesse.backend.post.api.query;

import com.noblesse.backend.post.common.dto.PostCoCommentDTO;
import com.noblesse.backend.post.query.application.service.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/posts/comments/comment")
@RequiredArgsConstructor
@Tag(name = "Post Co Comment Query Controller")
public class PostCoCommentQueryController {

    private final PostQueryService postQueryService;

    @Operation(summary = "게시물 대댓글 ID로 게시물 대댓글 조회")
    @GetMapping(value = "/{postCoCommentId}")
    public ResponseEntity<PostCoCommentDTO> getPostCoCommentById(
            @PathVariable("postCoCommentId") Long postCoCommentId
    ) {
        PostCoCommentDTO postCoComment = postQueryService.getPostCoCommentById(postCoCommentId);

        return ResponseEntity.ok(postCoComment);
    }

    @Operation(summary = "게시물 대댓글 목록 조회")
    @GetMapping
    public ResponseEntity<List<PostCoCommentDTO>> getPostCoComments() {
        List<PostCoCommentDTO> postCoComments = postQueryService.getAllPostCoComments();

        return ResponseEntity.ok(postCoComments);
    }
    
    @Operation(summary = "특정 사용자의 게시물의 모든 대댓글 조회")
    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<List<PostCoCommentDTO>> getPostCoCommentsByUserId(
            @PathVariable Long userId
    ) {
        List<PostCoCommentDTO> postCoComments = postQueryService.getPostCoCommentsByUserId(userId);

        return ResponseEntity.ok(postCoComments);
    }
}
