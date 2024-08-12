package com.noblesse.backend.post.query.application.dto;

import com.noblesse.backend.post.common.entity.PostComment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCommentDTO {
    private Long postCommentId;
    private String postCommentContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Long userId;
    private Long postId;
    private List<PostCoCommentDTO> coComments;

    public PostCommentDTO(PostComment comment) {
        this.postCommentId = comment.getPostCommentId();
        this.postCommentContent = comment.getPostCommentContent();
        this.createdDateTime = comment.getCreatedDateTime();
        this.updatedDateTime = comment.getUpdatedDateTime();
        this.userId = comment.getUserId();
        this.postId = comment.getPostId();
    }
}
