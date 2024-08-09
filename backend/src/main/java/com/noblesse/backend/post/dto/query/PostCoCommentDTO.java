package com.noblesse.backend.post.dto.query;

import com.noblesse.backend.post.entity.PostCoComment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCoCommentDTO {
    private String postCommentContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Long userId;
    private Long postCommentId;

    public PostCoCommentDTO(PostCoComment coComment) {
        this.postCommentContent = coComment.getPostCommentContent();
        this.createdDateTime = coComment.getCreatedDateTime();
        this.updatedDateTime = coComment.getUpdatedDateTime();
        this.userId = coComment.getUserId();
        this.postCommentId = coComment.getPostCommentId();
    }
}
