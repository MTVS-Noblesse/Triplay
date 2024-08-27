package com.noblesse.backend.post.command.domain.publisher;

import com.noblesse.backend.post.command.domain.event.PostCommentCreatedEvent;
import com.noblesse.backend.post.command.domain.event.PostCommentDeletedEvent;
import com.noblesse.backend.post.command.domain.event.PostCommentUpdatedEvent;
import com.noblesse.backend.post.common.entity.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCommentEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishPostCommentCreatedEvent(PostComment postComment) {
        PostCommentCreatedEvent event = new PostCommentCreatedEvent(postComment);
        eventPublisher.publishEvent(event);
    }

    public void publishPostCommentUpdatedEvent(PostComment postComment) {
        PostCommentUpdatedEvent event = new PostCommentUpdatedEvent(postComment);
        eventPublisher.publishEvent(event);
    }

    public void publishPostCommentDeletedEvent(PostComment postComment) {
        PostCommentDeletedEvent event = new PostCommentDeletedEvent(postComment);
        eventPublisher.publishEvent(event);
    }
}
