package com.noblesse.backend.bookmark;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "BookMark")
@Table(name = "bookmark")
public class BookMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKMARK_ID")
    private Long bookMarkId;

    @Column(name = "IS_BOOKMARKED") // 북마크 여부
    private Boolean isBookMarked;

    @Column(name = "CREATED_DATETIME") // 생성 일시
    private LocalDateTime createdDateTime;

    @Column(name = "UPDATED_DATETIME") // 수정 일시
    private LocalDateTime updatedDateTime;

    @Column(name = "USER_ID") // 사용자(User) USER_ID
    private Long userId;

    protected BookMark() {}

    public BookMark(Boolean isBookMarked, LocalDateTime createdDateTime, LocalDateTime updatedDateTime, Long userId) {
        this.isBookMarked = isBookMarked;
        this.createdDateTime = createdDateTime;
        this.updatedDateTime = updatedDateTime;
        this.userId = userId;
    }

    public Long getBookMarkId() {
        return bookMarkId;
    }

    public Boolean getBookMarked() {
        return isBookMarked;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "BookMark{" +
                "bookMarkId=" + bookMarkId +
                ", isBookMarked=" + isBookMarked +
                ", createdDateTime=" + createdDateTime +
                ", updatedDateTime=" + updatedDateTime +
                ", userId=" + userId +
                '}';
    }
}
