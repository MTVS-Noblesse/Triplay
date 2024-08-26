package com.noblesse.backend.post.common.dto;

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
    private String postCoCommentContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Long userId;
    private Long postCommentId;

    // Create 용 생성자
    public PostCoCommentDTO(String postCoCommentContent, Long userId, Long postCommentId) {
        this.postCoCommentContent = postCoCommentContent;
        this.userId = userId;
        this.postCommentId = postCommentId;
    }

    // Update 용 생성자
    public PostCoCommentDTO(Long postCoCommentId, String postCoCommentContent, Long userId) {
        this.postCoCommentId = postCoCommentId;
        this.postCoCommentContent = postCoCommentContent;
        this.userId = userId;
    }

    // Delete 용 생성자
    public PostCoCommentDTO(Long postCoCommentId, Long userId) {
        this.postCoCommentId = postCoCommentId;
        this.userId = userId;
    }


    // Entity로부터 DTO를 생성하는 생성자
    public PostCoCommentDTO(PostCoComment coComment) {
        this.postCoCommentId = coComment.getPostCoCommentId();
        this.postCoCommentContent = coComment.getPostCoCommentContent();
        this.createdDateTime = coComment.getCreatedDateTime();
        this.updatedDateTime = coComment.getUpdatedDateTime();
        this.userId = coComment.getUserId();
        this.postCommentId = coComment.getPostComment() != null ? coComment.getPostComment().getPostCommentId() : null;
    }
}
