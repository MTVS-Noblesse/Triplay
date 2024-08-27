package com.noblesse.backend.post.command.domain.event;

import com.noblesse.backend.post.common.entity.PostCoComment;
import lombok.Getter;

@Getter
public class PostCoCommentDeletedEvent {
    private final PostCoComment postCoComment;

    public PostCoCommentDeletedEvent(PostCoComment postCoComment) {
        this.postCoComment = postCoComment;
    }
}
