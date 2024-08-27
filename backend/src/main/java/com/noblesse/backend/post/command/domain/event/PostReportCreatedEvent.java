package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostReport;
import lombok.Getter;

@Getter
public class PostReportCreatedEvent {
    private final PostReport postReport;

    public PostReportCreatedEvent(PostReport postReport) {
        this.postReport = postReport;
    }
}
