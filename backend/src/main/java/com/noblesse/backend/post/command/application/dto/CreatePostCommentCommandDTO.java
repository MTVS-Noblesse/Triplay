package com.noblesse.backend.post.command.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostCommentCommandDTO {
    private Long postCommentId;
    private String postCommentContent;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Long userId;
    private Long postId;
}
