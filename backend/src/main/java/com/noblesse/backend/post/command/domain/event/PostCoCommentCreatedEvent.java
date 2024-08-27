package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostCoComment;
import lombok.Getter;

@Getter
public class PostCoCommentCreatedEvent {
    private final PostCoComment postCoComment;

    public PostCoCommentCreatedEvent(PostCoComment postCoComment) {
        this.postCoComment = postCoComment;
    }
}
