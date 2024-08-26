package com.noblesse.backend.post.common.dto;

import com.noblesse.backend.post.common.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long postId;
    private String postTitle;
    private String postContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Boolean isOpened;
    private Long userId;
    private Long tripId;
    private Long clipId;
    private List<PostCommentDTO> comments;

    // Create 용 생성자
    public PostDTO(String postTitle, String postContent, Boolean isOpened, Long userId, Long tripId, Long clipId) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.isOpened = isOpened;
        this.userId = userId;
        this.tripId = tripId;
        this.clipId = clipId;
    }

    // Update 용 생성자
    public PostDTO(Long postId, String postTitle, String postContent, Boolean isOpened, Long userId) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.isOpened = isOpened;
        this.userId = userId;
    }

    // Delete 용 생성자
    public PostDTO(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

    // Entity로부터 DTO를 생성하는 생성자
    public PostDTO(Post post) {
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
        this.createdDateTime = post.getCreatedDateTime();
        this.updatedDateTime = post.getUpdatedDateTime();
        this.isOpened = post.getIsOpened();
        this.userId = post.getUserId();
        this.tripId = post.getTripId();
        this.clipId = post.getClipId();
    }
}
