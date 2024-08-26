package com.noblesse.backend.clip.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "ClipReport")
@Table(name = "CLIP_REPORT")
public class ClipReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIP_REPORT_ID")
    private Long clipReportId;

    @Column(name = "REPORT_CATEGORY_ID")
    private Long reportCategoryId;

    @Column(name = "CLIP_REPORT_TITLE")
    private String clipReportTitle;

    @Column(name = "CLIP_REPORT_CONTENT")
    private String clipReportContent;

    @CreationTimestamp
    @Column(name = "CLIP_REPORT_DATETIME")
    private LocalDateTime clipReportDateTime;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CLIP_ID")
    private Long clipId;

    protected ClipReport() {}

    public ClipReport(Long reportCategoryId, String clipReportTitle, String clipReportContent, Long userId, Long clipId) {
        this.reportCategoryId = reportCategoryId;
        this.clipReportTitle = clipReportTitle;
        this.clipReportContent = clipReportContent;
        this.userId = userId;
        this.clipId = clipId;
    }

    public LocalDateTime getClipReportDateTime() {
        return clipReportDateTime;
    }

    public String getClipReportTitle() {
        return clipReportTitle;
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
