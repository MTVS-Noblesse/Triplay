package com.noblesse.backend.post.query.application.dto;

import com.noblesse.backend.post.common.entity.PostCoComment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCoCommentDTO {
    private Long postCoCommentId;
    private String postCommentContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Long userId;
    private Long postCommentId;

    public PostCoCommentDTO(PostCoComment coComment) {
        this.postCoCommentId = coComment.getPostCoCommentId();
        this.postCommentContent = coComment.getPostCommentContent();
        this.createdDateTime = coComment.getCreatedDateTime();
        this.updatedDateTime = coComment.getUpdatedDateTime();
        this.userId = coComment.getUserId();
        this.postCommentId = coComment.getPostCommentId();
    }
}
