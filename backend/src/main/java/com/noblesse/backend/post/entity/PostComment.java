package com.noblesse.backend.post.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "PostComment")
@Table(name = "POST_COMMENT")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_COMMENT_ID") // POST COMMENT PK
    private Long postCommentId;

    @Column(name = "POST_COMMENT_CONTENT") // POST COMMENT 본문
    private String postCommentContent;

    @Column(name = "CREATED_DATETIME") // POST COMMENT 생성 일시
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATETIME") // POST COMMENT 수정 일시
    private LocalDateTime updatedDateTime;

    @Column(name = "USER_ID") // POST COMMENT 작성 유저 ID
    private Long userId;

    @Column(name = "POST_ID") // POST COMMENT 달린 포스트 ID
    private Long postId;

    protected PostComment() {
    }

    public PostComment(Long postCommentId, String postCommentContent, LocalDateTime createdDateTime, LocalDateTime updatedDateTime, Long userId, Long postId) {
        this.postCommentId = postCommentId;
        this.postCommentContent = postCommentContent;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.userId = userId;
        this.postId = postId;
    }

    public Long getPostCommentId() {
        return postCommentId;
    }

    public String getPostCommentContent() {
        return postCommentContent;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }

    @Override
    public String toString() {
        return "PostComment{" +
                "postCommentId=" + postCommentId +
                ", postCommentContent='" + postCommentContent + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", updatedDateTime=" + updatedDateTime +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
