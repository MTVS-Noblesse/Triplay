package com.noblesse.backend.post.command.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostCommandDTO {
    private Long postId;
    private String postTitle;
    private String postContent;
    private Boolean isOpened;
    private Long userId;
}
