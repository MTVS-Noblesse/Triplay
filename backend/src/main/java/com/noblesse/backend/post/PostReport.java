package com.noblesse.backend.post;

import jakarta.persistence.*;

@Entity(name = "PostReport")
@Table(name = "POST_REPORT")
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_REPORT_ID")
    private Long postReportId;

    @Column(name = "POST_REPORT_CONTENT")
    private String postReportContent;

    @Column(name = "REPORT_CATEGORY_ID")
    private Long reportCategoryId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "POST_ID")
    private Long postId;

    protected PostReport() {}

    public PostReport(String postReportContent, Long reportCategoryId, Long userId, Long postId) {
        this.postReportContent = postReportContent;
        this.reportCategoryId = reportCategoryId;
        this.userId = userId;
        this.postId = postId;
    }

    public Long getPostReportId() {
        return postReportId;
    }

    public String getPostReportContent() {
        return postReportContent;
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

    @Override
    public String toString() {
        return "PostReport{" +
                "postReportId=" + postReportId +
                ", postReportContent='" + postReportContent + '\'' +
                ", reportCategoryId=" + reportCategoryId +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
