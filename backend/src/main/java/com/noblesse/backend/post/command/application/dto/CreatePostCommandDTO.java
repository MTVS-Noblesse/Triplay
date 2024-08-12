package com.noblesse.backend.post.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostCommandDTO {
    private Long postId;
    private String postTitle;
    private String postContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Boolean isOpened;
    private Long userId;
    private Long tripId;
    private Long clipId;
}
