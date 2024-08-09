package com.noblesse.backend.post;

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

    @Column(name = "WRITTEN_DATETIME") // POST COMMENT 단 일시
    private LocalDateTime writtenDatetime;

    @Column(name = "USER_ID") // POST COMMENT 작성 유저 ID
    private Long userId;

    @Column(name = "POST_ID") // POST COMMENT 달린 포스트 ID
    private Long postId;

    protected PostComment() {
    }

    public PostComment(String postCommentContent, LocalDateTime writtenDatetime, Long userId, Long postId) {
        this.postCommentContent = postCommentContent;
        this.writtenDatetime = writtenDatetime;
        this.userId = userId;
        this.postId = postId;
    }

    public Long getPostCommentId() {
        return postCommentId;
    }

    public String getPostCommentContent() {
        return postCommentContent;
    }

    public LocalDateTime getWrittenDatetime() {
        return writtenDatetime;
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
                ", writtenDatetime=" + writtenDatetime +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
