package com.noblesse.backend.post.common.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "PostCoComment")
@Table(name = "POST_CO_COMMENT")
public class PostCoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_CO_COMMENT_ID") // POST CO COMMENT PK
    private Long postCoCommentId;

    @Column(name = "POST_CO_COMMENT_CONTENT") // POST CO COMMENT 본문
    private String postCommentContent;

    @Column(name = "CREATED_DATETIME") // POST CO COMMENT 생성 일시
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATETIME") // POST CO COMMENT 수정 일시
    private LocalDateTime updatedDateTime;

    @Column(name = "USER_ID") // POST CO COMMENT 작성 유저 ID
    private Long userId;

    @Column(name = "POST_COMMENT_ID")
    private Long postCommentId;

    protected PostCoComment() {
    }

    public PostCoComment(Long postCoCommentId, String postCommentContent, LocalDateTime createdDateTime, LocalDateTime updatedDateTime, Long userId, Long postCommentId) {
        this.postCoCommentId = postCoCommentId;
        this.postCommentContent = postCommentContent;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.userId = userId;
        this.postCommentId = postCommentId;
    }

    public Long getPostCoCommentId() {
        return postCoCommentId;
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

    public Long getPostCommentId() {
        return postCommentId;
    }

    @Override
    public String toString() {
        return "PostCoComment{" +
                "postCoCommentId=" + postCoCommentId +
                ", postCommentContent='" + postCommentContent + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", updatedDateTime=" + updatedDateTime +
                ", userId=" + userId +
                ", postCommentId=" + postCommentId +
                '}';
    }
}
