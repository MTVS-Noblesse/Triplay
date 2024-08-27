package com.noblesse.backend.post.command.domain.publisher;

import com.noblesse.backend.post.command.domain.event.PostReportCreatedEvent;
import com.noblesse.backend.post.command.domain.event.PostReportDeletedEvent;
import com.noblesse.backend.post.command.domain.event.PostReportUpdatedEvent;
import com.noblesse.backend.post.common.entity.PostReport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReportEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishPostReportCreatedEvent(PostReport postReport) {
        PostReportCreatedEvent event = new PostReportCreatedEvent(postReport);
        eventPublisher.publishEvent(event);
    }

    public void publishPostReportUpdatedEvent(PostReport postReport) {
        PostReportUpdatedEvent event = new PostReportUpdatedEvent(postReport);
        eventPublisher.publishEvent(event);
    }

    public void publishPostReportDeletedEvent(PostReport postReport) {
        PostReportDeletedEvent event = new PostReportDeletedEvent(postReport);
        eventPublisher.publishEvent(event);
    }
}
