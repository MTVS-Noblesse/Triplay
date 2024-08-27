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
@RequestMapping(value = "/postReport")
@RequiredArgsConstructor
@Tag(name = "Post Report Query Controller")
public class PostReportQueryController {

    private final PostQueryService postQueryService;

    @Operation(summary = "모든 포스트 신고 조회")
    @GetMapping
    public ResponseEntity<List<PostReportDTO>> getAllPostReports() {
        List<PostReportDTO> postReports = postQueryService.getAllPostReports();
        return ResponseEntity.ok(postReports);
    }

    @Operation(summary = "포스트에 따른 전체 신고 조회")
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostReportDTO>> getPostReportsByPostId(@PathVariable("postId") Long postId) {
        List<PostReportDTO> postReports = postQueryService.getPostReportsByPostId(postId);
        return ResponseEntity.ok(postReports);
    }

    @Operation(summary = "포스트 신고 상세 조회")
    @GetMapping("/{postReportId}")
    public ResponseEntity<PostReportDTO> getPostReportById(@PathVariable("postReportId") Long postReportId) {
        PostReportDTO postReport = postQueryService.getPostReportById(postReportId);
        return ResponseEntity.ok(postReport);
    }
}
