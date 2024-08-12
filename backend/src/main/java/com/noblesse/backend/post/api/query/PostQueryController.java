package com.noblesse.backend.post.api.query;

import com.noblesse.backend.post.query.application.dto.PostDTO;
import com.noblesse.backend.post.query.application.service.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<PostDTO>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PostDTO> posts = postQueryService.getAllPosts(pageRequest);

        return ResponseEntity.ok(posts);
    }
    
    @Operation(summary = "특정 사용자의 게시물 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Long userId) {
        List<PostDTO> posts = postQueryService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }
}
