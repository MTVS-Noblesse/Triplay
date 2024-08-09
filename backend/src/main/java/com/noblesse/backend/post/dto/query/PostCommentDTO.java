package com.noblesse.backend.post.dto.query;

import com.noblesse.backend.post.entity.Post;
import com.noblesse.backend.post.entity.PostComment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCommentDTO {
    private String postCommentContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Long userId;
    private Long postId;

    public PostCommentDTO(PostComment comment) {
        this.postCommentContent = comment.getPostCommentContent();
        this.createdDateTime = comment.getCreatedDateTime();
        this.updatedDateTime = comment.getUpdatedDateTime();
        this.userId = comment.getUserId();
        this.postId = comment.getPostId();
    }
}
