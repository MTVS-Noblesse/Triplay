package com.noblesse.backend.clip;

import jakarta.persistence.*;

@Entity(name = "ClipReport")
@Table(name = "CLIP_REPORT")
public class ClipReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIP_REPORT_ID")
    private Long clipReportId;

    @Column(name = "REPORT_CATEGORY_ID")
    private Long reportCategoryId;

    @Column(name = "CLIP_REPORT_CONTENT")
    private String clipReportContent;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CLIP_ID")
    private Long clipId;

    protected ClipReport() {}

    public ClipReport(Long reportCategoryId, String clipReportContent, Long userId, Long clipId) {
        this.reportCategoryId = reportCategoryId;
        this.clipReportContent = clipReportContent;
        this.userId = userId;
        this.clipId = clipId;
    }

    public Long getClipReportId() {
        return clipReportId;
    }

    public Long getReportCategoryId() {
        return reportCategoryId;
    }

    public String getClipReportContent() {
        return clipReportContent;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getClipId() {
        return clipId;
    }

    @Override
    public String toString() {
        return "ClipReport{" +
                "clipReportId=" + clipReportId +
                ", reportCategoryId=" + reportCategoryId +
                ", clipReportContent='" + clipReportContent + '\'' +
                ", userId=" + userId +
                ", clipId=" + clipId +
                '}';
    }
}
