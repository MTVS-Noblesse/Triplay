package com.noblesse.backend.post;

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

    @Column(name = "WRITTEN_DATETIME") // POST CO COMMENT 단 일시
    private LocalDateTime writtenDatetime;

    @Column(name = "USER_ID") // POST CO COMMENT 작성 유저 ID
    private Long userId;

    @Column(name = "POST_COMMENT_ID")
    private Long postCommentId;

    protected PostCoComment() {
    }

    public PostCoComment(String postCommentContent, LocalDateTime writtenDatetime, Long userId, Long postCommentId) {
        this.postCommentContent = postCommentContent;
        this.writtenDatetime = writtenDatetime;
        this.userId = userId;
        this.postCommentId = postCommentId;
    }

    public Long getPostCoCommentId() {
        return postCoCommentId;
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

    public Long getPostCommentId() {
        return postCommentId;
    }

    @Override
    public String toString() {
        return "PostCoComment{" +
                "postCoCommentId=" + postCoCommentId +
                ", postCommentContent='" + postCommentContent + '\'' +
                ", writtenDatetime=" + writtenDatetime +
                ", userId=" + userId +
                ", postCommentId=" + postCommentId +
                '}';
    }
}
