package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostReport;
import lombok.Getter;

@Getter
public class PostReportUpdatedEvent {
    private final PostReport postReport;

    public PostReportUpdatedEvent(PostReport postReport) {
        this.postReport = postReport;
    }
}
