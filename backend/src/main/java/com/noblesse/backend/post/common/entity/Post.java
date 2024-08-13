package com.noblesse.backend.post.common.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity(name = "Post")
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")  // 포스트 PK code
    private Long postId;

    @Column(name = "POST_TITLE") // 포스트 제목
    private String postTitle;

    @Column(name = "POST_CONTENT") // 포스트 본문(?)
    private String postContent;

    @Column(name = "CREATED_DATETIME") // 생성 일시
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATETIME") // 수정 일시
    private LocalDateTime updatedDateTime;

    @Column(name = "IS_OPENED") // 공개 여부
    private Boolean isOpened;

    @Column(name = "USER_ID") // 포스트 USER_ID
    private Long userId;

    @Column(name = "TRIP_ID") // 해당 여행 TRIP_CODE
    private Long tripId;

    @Column(name = "CLIP_ID")
    private Long clipId;

    protected Post() {
    }

    @Builder
    public Post(Long postId, String postTitle, String postContent, LocalDateTime createdDateTime, LocalDateTime updatedDateTime, Boolean isOpened, Long userId, Long tripId, Long clipId) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.isOpened = isOpened;
        this.userId = userId;
        this.tripId = tripId;
        this.clipId = clipId;
    }

    public void updatePost(String postContent) {
        this.postContent = postContent;
    }

    public void updatePost(String postTitle, String postContent, Boolean isOpened) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.isOpened = isOpened;
        this.updatedDateTime = LocalDateTime.now();
    }

    public Long getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public Boolean getIsOpened() {
        return isOpened;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTripId() {
        return tripId;
    }

    public Long getClipId() {
        return clipId;
    }

    @Override
    public String toString() {
        return "Post [" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", createdDateTime=" + createdDateTime +
                ", updatedDateTime=" + updatedDateTime +
                ", isOpened=" + isOpened +
                ", userId=" + userId +
                ", tripId=" + tripId +
                ", clipId=" + clipId +
                ']';
    }
}
