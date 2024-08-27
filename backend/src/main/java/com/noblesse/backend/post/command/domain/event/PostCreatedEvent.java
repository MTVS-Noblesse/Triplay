package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.Post;
import lombok.Getter;

@Getter
public class PostCreatedEvent {
    private final Post post;

    public PostCreatedEvent(Post post) {
        this.post = post;
    }
}
