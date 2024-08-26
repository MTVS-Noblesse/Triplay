package com.noblesse.backend.post.api.query;

import com.noblesse.backend.post.common.dto.PostReportDTO;
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
@RequestMapping(value = "/api/posts/report")
@RequiredArgsConstructor
@Tag(name = "Post Report Query Controller")
public class PostReportQueryController {

    private final PostQueryService postQueryService;

    @Operation(summary = "게시물 신고 ID로 게시물 신고글 조회")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PostReportDTO> getPostReportById(@PathVariable("id") Long id) {
        PostReportDTO postReport = postQueryService.getPostReportById(id);
        return ResponseEntity.ok(postReport);
    }

    @Operation(summary = "게시물 신고글 목록 조회")
    @GetMapping
    public ResponseEntity<List<PostReportDTO>> getPostReports() {
        List<PostReportDTO> postReports = postQueryService.getAllPostReports();

        return ResponseEntity.ok(postReports);
    }
    
    @Operation(summary = "특정 사용자의 게시물 신고글 조회")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostReportDTO>> getPostReportsByUserId(@PathVariable Long userId) {
        List<PostReportDTO> postReports = postQueryService.getPostReportsByUserId(userId);
        return ResponseEntity.ok(postReports);
    }
}
