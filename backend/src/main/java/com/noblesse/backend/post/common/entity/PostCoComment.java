package com.noblesse.backend.post.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "PostCoComment")
@Table(name = "post_co_comment")
@ToString
public class PostCoComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_co_comment_id") // POST CO COMMENT PK
    private Long postCoCommentId;

    @Column(name = "post_co_comment_content") // POST CO COMMENT 본문
    private String postCoCommentContent;

    @Column(name = "created_datetime") // POST CO COMMENT 생성 일시
    private LocalDateTime createdDateTime;

    @Column(name = "updated_datetime") // POST CO COMMENT 수정 일시
    private LocalDateTime updatedDateTime;

    @Column(name = "user_id") // POST CO COMMENT 작성 유저 ID
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_comment_id")
    private PostComment postComment;

    protected PostCoComment() {
    }

    @Builder
    public PostCoComment(Long postCoCommentId, String postCoCommentContent, LocalDateTime createdDateTime, LocalDateTime updatedDateTime, Long userId, PostComment postComment) {
        this.postCoCommentId = postCoCommentId;
        this.postCoCommentContent = postCoCommentContent;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.userId = userId;
        this.postComment = postComment;
    }

    public void updatePostCoComment(String postCoCommentContent) {
        this.postCoCommentContent = postCoCommentContent;
        this.updatedDateTime = LocalDateTime.now();
    }

    public Long getPostCoCommentId() {
        return postCoCommentId;
    }

    public String getPostCoCommentContent() {
        return postCoCommentContent;
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

    public PostComment getPostComment() {
        return postComment;
    }

    public void setPostComment(PostComment postComment) {
        this.postComment = postComment;
    }
}
