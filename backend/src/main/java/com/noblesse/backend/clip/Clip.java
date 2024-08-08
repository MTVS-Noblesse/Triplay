package com.noblesse.backend.clip;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Clip")
@Table(name = "clip")
public class Clip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIP_ID") // 클립 PK code
    private Long clipId;

    @Column(name = "CLIP_TITLE") // 클립 제목
    private String clipTitle;

    @Column(name = "CLIP_URL") // 클립 저장 URL
    private String clipUrl;

    @Column(name = "UPLOAD_DATETIME") // 업로드 일시
    private LocalDateTime uploadDatetime;

    @Column(name = "IS_OPENED") // 공개 여부
    private Boolean isOpened;

    @Column(name = "USER_ID") // 클립 USER_ID
    private Long userId;

    @Column(name = "TRIP_ID") // 해당 여행 TRIP_CODE
    private Long tripId;
}
