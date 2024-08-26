package com.noblesse.backend.clip.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClipCoCommentRegistRequestDTO {
    private String clipCoCommentContent;
    private Long userId;
    private Long clipCommentId;
}
