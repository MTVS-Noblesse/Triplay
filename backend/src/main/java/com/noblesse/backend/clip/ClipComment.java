package com.noblesse.backend.clip;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "ClipComment")
@Table(name = "CLIP_COMMENT")
public class ClipComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIP_COMMENT_CODE") // CLIP COMMENT PK
    private Long clipCommentId;

    @Column(name = "CLIP_COMMENT_CONTENT") // CLIP COMMENT 내용
    private String clipCommentContent;

    @Column(name = "WRITTEN_DATETIME") // COMMENT 단 일시
    private LocalDateTime writtenDatetime;

    @Column(name = "USER_ID") // Comment 쓴 유저 ID
    private Long userId;

    @Column(name = "CLIP_ID") // Comment 달린 CLIP ID
    private Long clipId;
}
