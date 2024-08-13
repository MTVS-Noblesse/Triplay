package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostComment;
import lombok.Getter;

@Getter
public class PostCommentCreatedEvent {
    private final PostComment postComment;

    public PostCommentCreatedEvent(PostComment postComment) {
        this.postComment = postComment;
    }
}
