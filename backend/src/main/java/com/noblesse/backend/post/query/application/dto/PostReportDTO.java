package com.noblesse.backend.post.query.application.dto;

import com.noblesse.backend.post.common.entity.PostReport;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostReportDTO {
    private String postReportContent;
    private Long reportCategoryId;
    private Long userId;
    private Long postId;

    public PostReportDTO(PostReport postReport) {
        this.postReportContent = postReport.getPostReportContent();
        this.reportCategoryId = postReport.getReportCategoryId();
        this.userId = postReport.getUserId();
        this.postId = postReport.getPostId();
    }
}
