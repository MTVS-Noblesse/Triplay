package com.noblesse.backend.post.command.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostCommentCommandDTO {
    private Long postCommentId;
    private String postCommentContent;
    private Long userId;
    private Long postId;
}
