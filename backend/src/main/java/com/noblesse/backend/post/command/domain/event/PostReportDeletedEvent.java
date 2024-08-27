package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostReport;
import lombok.Getter;

@Getter
public class PostReportDeletedEvent {
    private final PostReport postReport;

    public PostReportDeletedEvent(PostReport postReport) {
        this.postReport = postReport;
    }
}
