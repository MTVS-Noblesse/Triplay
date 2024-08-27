package com.noblesse.backend.post.common.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity(name = "Post")
@Table(name = "post")
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")  // 포스트 PK code
    private Long postId;

    @Column(name = "post_title") // 포스트 제목
    private String postTitle;

    @Column(name = "post_content") // 포스트 본문(?)
    private String postContent;

    @Column(name = "written_datetime") // 생성 일시
    private LocalDateTime writtenDatetime;

    @Column(name = "modified_datetime") // 수정 일시
    private LocalDateTime modifiedDatetime;

    @Column(name = "is_opened") // 공개 여부
    private Boolean isOpened;

    @Column(name = "user_id") // 포스트 USER_ID
    private Long userId;

    @Column(name = "trip_id") // 해당 여행 TRIP_CODE
    private Long tripId;

    @Column(name = "clip_id")
    private Long clipId;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PostComment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PostReport> reports = new ArrayList<>();

    protected Post() {
    }

    @Builder
    public Post(Long postId, String postTitle, String postContent, LocalDateTime writtenDatetime, LocalDateTime modifiedDatetime, Boolean isOpened, Long userId, Long tripId, Long clipId) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.writtenDatetime = writtenDatetime;
        this.modifiedDatetime = modifiedDatetime;
        this.isOpened = isOpened;
        this.userId = userId;
        this.tripId = tripId;
        this.clipId = clipId;
    }

    public void updatePost(String postTitle, String postContent, Boolean isOpened) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.isOpened = isOpened;
        this.modifiedDatetime = LocalDateTime.now();
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

    public LocalDateTime getWrittenDatetime() {
        return writtenDatetime;
    }

    public LocalDateTime getModifiedDatetime() {
        return modifiedDatetime;
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
}
