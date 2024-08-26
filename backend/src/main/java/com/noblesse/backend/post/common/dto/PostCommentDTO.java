package com.noblesse.backend.post.common.dto;

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

    // Create 용 생성자
    public PostCommentDTO(String postCommentContent, Long userId, Long postId) {
        this.postCommentContent = postCommentContent;
        this.userId = userId;
        this.postId = postId;
    }

    // Update 용 생성자
    public PostCommentDTO(Long postCommentId, String postCommentContent, Long userId) {
        this.postCommentId = postCommentId;
        this.postCommentContent = postCommentContent;
        this.userId = userId;
    }

    // Delete 용 생성자
    public PostCommentDTO(Long postCommentId, Long userId) {
        this.postCommentId = postCommentId;
        this.userId = userId;
    }

    // Entity로부터 DTO를 생성하는 생성자
    public PostCommentDTO(PostComment postComment) {
        this.postCommentId = postComment.getPostCommentId();
        this.postCommentContent = postComment.getPostCommentContent();
        this.createdDateTime = postComment.getCreatedDateTime();
        this.updatedDateTime = postComment.getUpdatedDateTime();
        this.userId = postComment.getUserId();
        this.postId = postComment.getPost() != null ? postComment.getPost().getPostId() : null;
    }
}
