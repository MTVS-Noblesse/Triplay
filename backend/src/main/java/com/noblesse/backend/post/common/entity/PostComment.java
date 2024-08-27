package com.noblesse.backend.post.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;

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

    @Column(name = "written_datetime") // POST COMMENT 생성 일시
    private LocalDateTime writtenDatetime;

    @Column(name = "modified_datetime") // POST COMMENT 수정 일시
    private LocalDateTime modifiedDatetime;

    @Column(name = "user_id") // POST COMMENT 작성 유저 ID
    private Long userId;

    @Column(name = "post_id")
    private Long postId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
//    private Post post; // POST COMMENT 달린 포스트 ID

//    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PostCoComment> coComments = new ArrayList<>();

    protected PostComment() {
    }

    @Builder
    public PostComment(Long postCommentId, String postCommentContent, LocalDateTime writtenDatetime, LocalDateTime modifiedDatetime, Long userId, Long postId) {
        this.postCommentId = postCommentId;
        this.postCommentContent = postCommentContent;
        this.writtenDatetime = writtenDatetime;
        this.modifiedDatetime = modifiedDatetime;
        this.userId = userId;
        this.postId = postId;
    }

    public void updatePostComment(String postCommentContent) {
        this.postCommentContent = postCommentContent;
        this.modifiedDatetime = LocalDateTime.now();
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

    public LocalDateTime getModifiedDatetime() {
        return modifiedDatetime;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }
}
