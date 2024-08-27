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
    private LocalDateTime createdDatetime;

    @Column(name = "processed_datetime") // 처리 일시
    private LocalDateTime processedDatetime;

    @Column(name = "report_category_id")
    private Long reportCategoryId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
//    private Post post;

    protected PostReport() {
    }

    @Builder
    public PostReport(Long postReportId, String postReportContent, Boolean isReported, LocalDateTime createdDatetime, LocalDateTime processedDatetime, Long reportCategoryId, Long userId, Long postId) {
        this.postReportId = postReportId;
        this.postReportContent = postReportContent;
        this.isReported = isReported;
        this.createdDatetime = createdDatetime;
        this.processedDatetime = processedDatetime;
        this.reportCategoryId = reportCategoryId;
        this.userId = userId;
        this.postId = postId;
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

    public Boolean getIsReported() {
        return isReported;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public LocalDateTime getProcessedDatetime() {
        return processedDatetime;
    }

    public Long getReportCategoryId() {
        return reportCategoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }

    //    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }
}
