package com.noblesse.backend.clip.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "IS_OPENED") // 공개 여부
    private Boolean isOpened;

    @CreationTimestamp
    @Column(name = "UPLOAD_DATETIME") // 업로드 일시
    private LocalDateTime uploadDatetime;

    @Column(name = "USER_ID") // 클립 USER_ID
    private Long userId;

    @Column(name = "TRIP_ID") // 해당 여행 TRIP_CODE
    private Long tripId;

    protected Clip() {}

    public Clip(String clipTitle, String clipUrl, Boolean isOpened, Long userId, Long tripId) {
        this.clipTitle = clipTitle;
        this.clipUrl = clipUrl;
        this.isOpened = isOpened;
        this.userId = userId;
        this.tripId = tripId;
    }

    public Long getClipId() {
        return clipId;
    }

    public String getClipTitle() {
        return clipTitle;
    }

    public String getClipUrl() {
        return clipUrl;
    }

    public LocalDateTime getUploadDatetime() {
        return uploadDatetime;
    }

    public Boolean getOpened() {
        return isOpened;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTripId() {
        return tripId;
    }

    @Override
    public String toString() {
        return "Clip{" +
                "clipId=" + clipId +
                ", clipTitle='" + clipTitle + '\'' +
                ", clipUrl='" + clipUrl + '\'' +
                ", uploadDatetime=" + uploadDatetime +
                ", isOpened=" + isOpened +
                ", userId=" + userId +
                ", tripId=" + tripId +
                '}';
    }
}
