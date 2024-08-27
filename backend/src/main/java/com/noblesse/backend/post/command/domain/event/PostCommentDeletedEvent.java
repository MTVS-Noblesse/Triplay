package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostComment;
import lombok.Getter;

@Getter
public class PostCommentDeletedEvent {
    private final PostComment postComment;

    public PostCommentDeletedEvent(PostComment postComment) {
        this.postComment = postComment;
    }
}
