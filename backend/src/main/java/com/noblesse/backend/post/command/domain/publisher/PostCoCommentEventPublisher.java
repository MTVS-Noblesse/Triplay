package com.noblesse.backend.post.command.domain.publisher;

import com.noblesse.backend.post.command.domain.event.PostCoCommentCreatedEvent;
import com.noblesse.backend.post.command.domain.event.PostCoCommentDeletedEvent;
import com.noblesse.backend.post.command.domain.event.PostCoCommentUpdatedEvent;
import com.noblesse.backend.post.common.entity.PostCoComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCoCommentEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishPostCoCommentCreatedEvent(PostCoComment postCoComment) {
        PostCoCommentCreatedEvent event = new PostCoCommentCreatedEvent(postCoComment);
        eventPublisher.publishEvent(event);
    }

    public void publishPostCoCommentUpdatedEvent(PostCoComment postCoComment) {
        PostCoCommentUpdatedEvent event = new PostCoCommentUpdatedEvent(postCoComment);
        eventPublisher.publishEvent(event);
    }

    public void publishPostCoCommentDeletedEvent(PostCoComment postCoComment) {
        PostCoCommentDeletedEvent event = new PostCoCommentDeletedEvent(postCoComment);
        eventPublisher.publishEvent(event);
    }
}
