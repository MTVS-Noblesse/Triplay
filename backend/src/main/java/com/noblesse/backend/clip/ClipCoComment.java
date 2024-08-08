package com.noblesse.backend.clip;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "ClipCoComment")
@Table(name = "CLIP_CO_COMMENT")
public class ClipCoComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIP_CO_COMMENT_ID")
    private Long clipCoCommentId;

    @Column(name = "CLIP_CO_COMMENT_CONTENT")
    private String clipCoCommentContent;

    @Column(name = "WRITTEN_DATETIME")
    private LocalDateTime writtenDatetime;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CLIP_COMMENT_ID")
    private Long clipCommentId;

    protected ClipCoComment() {}

    public ClipCoComment(String clipCoCommentContent, LocalDateTime writtenDatetime, Long userId, Long clipCommentId) {
        this.clipCoCommentContent = clipCoCommentContent;
        this.writtenDatetime = writtenDatetime;
        this.userId = userId;
        this.clipCommentId = clipCommentId;
    }

    public Long getClipCoCommentId() {
        return clipCoCommentId;
    }

    public String getClipCoCommentContent() {
        return clipCoCommentContent;
    }

    public LocalDateTime getWrittenDatetime() {
        return writtenDatetime;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getClipCommentId() {
        return clipCommentId;
    }

    @Override
    public String toString() {
        return "ClipCoComment{" +
                "clipCoCommentId=" + clipCoCommentId +
                ", clipCoCommentContent='" + clipCoCommentContent + '\'' +
                ", writtenDatetime=" + writtenDatetime +
                ", userId=" + userId +
                ", clipCommentId=" + clipCommentId +
                '}';
    }
}
