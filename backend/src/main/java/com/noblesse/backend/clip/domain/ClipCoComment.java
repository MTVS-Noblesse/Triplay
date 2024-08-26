package com.noblesse.backend.clip.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(name = "WRITTEN_DATETIME")
    private LocalDateTime writtenDatetime;

    @UpdateTimestamp
    @Column(name = "MODIFIED_DATETIME")
    private LocalDateTime modifiedDatetime;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "CLIP_COMMENT_ID")
    private Long clipCommentId;

    protected ClipCoComment() {}

    public ClipCoComment(String clipCoCommentContent, Long userId, Long clipCommentId) {
        this.clipCoCommentContent = clipCoCommentContent;
        this.userId = userId;
        this.clipCommentId = clipCommentId;
    }

    public LocalDateTime getModifiedDatetime() {
        return modifiedDatetime;
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
                ", modifiedDatetime=" + modifiedDatetime +
                ", userId=" + userId +
                ", clipCommentId=" + clipCommentId +
                '}';
    }
}
