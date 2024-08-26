package com.noblesse.backend.post.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(name = "PostComment")
@Table(name = "post_comment")
@ToString
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_comment_id") // POST COMMENT PK
    private Long postCommentId;

    @Column(name = "post_comment_content") // POST COMMENT 본문
    private String postCommentContent;

    @Column(name = "created_datetime") // POST COMMENT 생성 일시
    private LocalDateTime createdDateTime;

    @Column(name = "updated_datetime") // POST COMMENT 수정 일시
    private LocalDateTime updatedDateTime;

    @Column(name = "user_id") // POST COMMENT 작성 유저 ID
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; // POST COMMENT 달린 포스트 ID

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostCoComment> coComments = new ArrayList<>();

    protected PostComment() {
    }

    @Builder
    public PostComment(Long postCommentId, String postCommentContent, LocalDateTime createdDateTime, LocalDateTime updatedDateTime, Long userId, Post post) {
        this.postCommentId = postCommentId;
        this.postCommentContent = postCommentContent;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.userId = userId;
        this.post = post;
    }

    public void updatePostComment(String postCommentContent) {
        this.postCommentContent = postCommentContent;
        this.updatedDateTime = LocalDateTime.now();
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
