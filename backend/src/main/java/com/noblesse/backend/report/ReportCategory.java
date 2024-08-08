package com.noblesse.backend.report;

import jakarta.persistence.*;

@Entity(name = "ReportCategory")
@Table(name = "REPORT_CATEGORY")
public class ReportCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_CATEGORY_ID")
    private Long reportCategoryId;

    @Column(name = "REPORT_CATEGORY_NAME")
    private String reportCategoryName;

    protected ReportCategory() {}

    public ReportCategory(String reportCategoryName) {
        this.reportCategoryName = reportCategoryName;
    }

    public Long getReportCategoryId() {
        return reportCategoryId;
    }

    public String getReportCategoryName() {
        return reportCategoryName;
    }

    @Override
    public String toString() {
        return "ReportCategory{" +
                "reportCategoryId=" + reportCategoryId +
                ", reportCategoryName='" + reportCategoryName + '\'' +
                '}';
    }
}
