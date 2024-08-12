package com.noblesse.backend.post.infrastructure.event;

import com.noblesse.backend.post.command.domain.event.PostCreatedEvent;
import com.noblesse.backend.post.command.domain.event.PostDeletedEvent;
import com.noblesse.backend.post.command.domain.event.PostUpdatedEvent;
import com.noblesse.backend.post.common.entity.Post;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PostEventListener {
    @EventListener
    public void handlePostCreatedEvent(PostCreatedEvent event) {
        Post post = event.getPost();
        
        System.out.println("# 새로운 포스트 작성: " + post.getPostTitle());
    }

    @EventListener
    public void handlePostUpdatedEvent(PostUpdatedEvent event) {
        Post post = event.getPost();

        System.out.println("# 포스트 수정: " + post.getPostTitle());
    }

    @EventListener
    public void handlePostDeletedEvent(PostDeletedEvent event) {
        Post post = event.getPost();

        System.out.println("# 포스트 삭제: " + post.getPostTitle());
    }
}
