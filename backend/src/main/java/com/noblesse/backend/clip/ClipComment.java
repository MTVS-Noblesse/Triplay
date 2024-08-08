package com.noblesse.backend.clip;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "ClipComment")
@Table(name = "CLIP_COMMENT")
public class ClipComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIP_COMMENT_ID") // CLIP COMMENT PK
    private Long clipCommentId;

    @Column(name = "CLIP_COMMENT_CONTENT") // CLIP COMMENT 내용
    private String clipCommentContent;

    @Column(name = "WRITTEN_DATETIME") // COMMENT 단 일시
    private LocalDateTime writtenDatetime;

    @Column(name = "USER_ID") // Comment 쓴 유저 ID
    private Long userId;

    @Column(name = "CLIP_ID") // Comment 달린 CLIP ID
    private Long clipId;

    protected ClipComment() {}

    public ClipComment(String clipCommentContent, LocalDateTime writtenDatetime, Long userId, Long clipId) {
        this.clipCommentContent = clipCommentContent;
        this.writtenDatetime = writtenDatetime;
        this.userId = userId;
        this.clipId = clipId;
    }

    public Long getClipCommentId() {
        return clipCommentId;
    }

    public String getClipCommentContent() {
        return clipCommentContent;
    }

    public LocalDateTime getWrittenDatetime() {
        return writtenDatetime;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getClipId() {
        return clipId;
    }

    @Override
    public String toString() {
        return "ClipComment{" +
                "clipCommentId=" + clipCommentId +
                ", clipCommentContent='" + clipCommentContent + '\'' +
                ", writtenDatetime=" + writtenDatetime +
                ", userId=" + userId +
                ", clipId=" + clipId +
                '}';
    }
}
