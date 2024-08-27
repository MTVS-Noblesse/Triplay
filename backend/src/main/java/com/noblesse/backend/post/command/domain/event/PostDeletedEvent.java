package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.Post;
import lombok.Getter;

@Getter
public class PostDeletedEvent {
    private final Post post;

    public PostDeletedEvent(Post post) {
        this.post = post;
    }
}
