package com.noblesse.backend.post.api.query;

import com.noblesse.backend.post.common.dto.PostDTO;
import com.noblesse.backend.post.query.application.service.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/posts")
@RequiredArgsConstructor
@Tag(name = "Post Query Controller")
public class PostQueryController {

    private final PostQueryService postQueryService;

    @Operation(summary = "게시물 ID로 게시물 조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long id) {
        PostDTO post = postQueryService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "게시물 목록 조회")
    @GetMapping
    public ResponseEntity<List<PostDTO>> getPosts() {
        List<PostDTO> posts = postQueryService.getAllPosts();

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "특정 사용자의 게시물 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Long userId) {
        List<PostDTO> posts = postQueryService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
}
