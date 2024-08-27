package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.Post;
import lombok.Getter;

@Getter
public class PostUpdatedEvent {
    private final Post post;

    public PostUpdatedEvent(Post post) {
        this.post = post;
    }
}
