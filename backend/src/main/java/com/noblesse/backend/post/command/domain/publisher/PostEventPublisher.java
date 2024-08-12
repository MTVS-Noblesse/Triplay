package com.noblesse.backend.post.command.domain.publisher;

import com.noblesse.backend.post.command.domain.event.PostCreatedEvent;
import com.noblesse.backend.post.command.domain.event.PostDeletedEvent;
import com.noblesse.backend.post.command.domain.event.PostUpdatedEvent;
import com.noblesse.backend.post.common.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishPostCreatedEvent(Post post) {
        PostCreatedEvent event = new PostCreatedEvent(post);
        eventPublisher.publishEvent(event);
    }

    public void publishPostUpdatedEvent(Post post) {
        PostUpdatedEvent event = new PostUpdatedEvent(post);
        eventPublisher.publishEvent(event);
    }

    public void publishPostDeletedEvent(Post post) {
        PostDeletedEvent event = new PostDeletedEvent(post);
        eventPublisher.publishEvent(event);
    }
}
