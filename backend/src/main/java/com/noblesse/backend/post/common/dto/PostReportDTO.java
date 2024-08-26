package com.noblesse.backend.post.common.dto;

import com.noblesse.backend.post.common.entity.PostReport;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostReportDTO {
    private Long postReportId;
    private String postReportContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime processedDateTime;
    private Boolean isReported;
    private Long reportCategoryId;
    private Long userId;
    private Long postId;

    // Create 용 생성자
    public PostReportDTO(String postReportContent, Boolean isReported, Long reportCategoryId, Long userId, Long postId) {
        this.postReportContent = postReportContent;
        this.isReported = isReported;
        this.reportCategoryId = reportCategoryId;
        this.userId = userId;
        this.postId = postId;
    }

    // Update 용 생성자
    public PostReportDTO(Long postReportId, Boolean isReported, Long userId) {
        this.postReportId = postReportId;
        this.isReported = isReported;
        this.userId = userId;
    }

    // Delete 용 생성자
    public PostReportDTO(Long postReportId, Long userId) {
        this.postReportId = postReportId;
        this.userId = userId;
    }

    // Entity로부터 DTO를 생성하는 생성자
    public PostReportDTO(PostReport postReport) {
        this.postReportId = postReport.getPostReportId();
        this.postReportContent = postReport.getPostReportContent();
        this.createdDateTime = postReport.getCreatedDateTime();
        this.processedDateTime = postReport.getProcessedDateTime();
        this.isReported = postReport.isReported();
        this.reportCategoryId = postReport.getReportCategoryId();
        this.userId = postReport.getUserId();
        this.postId = postReport.getPost() != null ? postReport.getPost().getPostId() : null;
    }
}
