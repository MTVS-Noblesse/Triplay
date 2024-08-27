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

    @Column(name = "written_datetime") // POST CO COMMENT 생성 일시
    private LocalDateTime writtenDatetime;

    @Column(name = "modified_datetime") // POST CO COMMENT 수정 일시
    private LocalDateTime modifiedDatetime;

    @Column(name = "user_id") // POST CO COMMENT 작성 유저 ID
    private Long userId;

    @Column(name = "post_comment_id")
    private Long postCommentId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_comment_id")
//    private PostComment postComment;

    protected PostCoComment() {
    }

    @Builder
    public PostCoComment(Long postCoCommentId, String postCoCommentContent, LocalDateTime writtenDatetime, LocalDateTime modifiedDatetime, Long userId, Long postCommentId) {
        this.postCoCommentId = postCoCommentId;
        this.postCoCommentContent = postCoCommentContent;
        this.writtenDatetime = writtenDatetime;
        this.modifiedDatetime = modifiedDatetime;
        this.userId = userId;
        this.postCommentId = postCommentId;
    }

    public void updatePostCoComment(String postCoCommentContent) {
        this.postCoCommentContent = postCoCommentContent;
        this.modifiedDatetime = LocalDateTime.now();
    }

    public Long getPostCoCommentId() {
        return postCoCommentId;
    }

    public String getPostCoCommentContent() {
        return postCoCommentContent;
    }

    public LocalDateTime getWrittenDatetime() {
        return writtenDatetime;
    }

    public LocalDateTime getModifiedDatetime() {
        return modifiedDatetime;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostCommentId() {
        return postCommentId;
    }

    //    public PostComment getPostComment() {
//        return postComment;
//    }
//
//    public void setPostComment(PostComment postComment) {
//        this.postComment = postComment;
//    }
}
