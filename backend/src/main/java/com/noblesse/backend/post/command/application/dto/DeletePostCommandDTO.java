package com.noblesse.backend.post.command.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostCommandDTO {
    private Long postId;
    private Long userId;
}
