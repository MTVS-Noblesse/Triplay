package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostCoComment;
import lombok.Getter;

@Getter
public class PostCoCommentUpdatedEvent {
    private final PostCoComment postCoComment;

    public PostCoCommentUpdatedEvent(PostCoComment postCoComment) {
        this.postCoComment = postCoComment;
    }
}
