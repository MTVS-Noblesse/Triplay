package com.noblesse.backend.post.dto.query;

import com.noblesse.backend.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String postTitle;
    private String postContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Boolean isOpened;
    private Long userId;
    private Long tripId;
    private Long clipId;

    public PostDTO(Post post) {
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
