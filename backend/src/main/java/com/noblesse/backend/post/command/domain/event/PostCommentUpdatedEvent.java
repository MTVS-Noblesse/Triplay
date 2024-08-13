package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostComment;
import lombok.Getter;

@Getter
public class PostCommentUpdatedEvent {
    private final PostComment postComment;

    public PostCommentUpdatedEvent(PostComment postComment) {
        this.postComment = postComment;
    }
}
