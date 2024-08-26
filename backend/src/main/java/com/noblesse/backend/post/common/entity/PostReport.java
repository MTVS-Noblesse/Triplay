package com.noblesse.backend.post.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "PostReport")
@Table(name = "post_report")
@ToString
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_report_id")
    private Long postReportId;

    @Column(name = "post_report_content")
    private String postReportContent;

    @Column(name = "is_reported") // 신고 유무(0 혹은 1)
    private Boolean isReported;

    @Column(name = "created_datetime") // 신고 접수 일시
    private LocalDateTime createdDateTime;

    @Column(name = "processed_datetime") // 처리 일시
    private LocalDateTime processedDateTime;

    @Column(name = "report_category_id")
    private Long reportCategoryId;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    protected PostReport() {
    }

    @Builder
    public PostReport(Long postReportId, String postReportContent, LocalDateTime createdDateTime, LocalDateTime processedDateTime, Boolean isReported, Long reportCategoryId, Long userId, Post post) {
        this.postReportId = postReportId;
        this.postReportContent = postReportContent;
        this.createdDateTime = createdDateTime;
        this.processedDateTime = processedDateTime;
        this.isReported = isReported;
        this.reportCategoryId = reportCategoryId;
        this.userId = userId;
        this.post = post;
    }

    public void updatePostReport(String postReportContent) {
        this.postReportContent = postReportContent;
    }

    public Long getPostReportId() {
        return postReportId;
    }

    public String getPostReportContent() {
        return postReportContent;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getProcessedDateTime() {
        return processedDateTime;
    }

    public Boolean isReported() {
        return isReported;
    }

    public Long getReportCategoryId() {
        return reportCategoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
