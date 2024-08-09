package com.noblesse.backend.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_nickname")
    private String userNickname;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="registed_at")
    private LocalDateTime registedAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="is_available")
    private boolean isAvailable;

    @Column(name="profile_url")
    private String profileUrl;

    public User() {
    }

    public User(Long userId, String userName, String userNickname, String userEmail, LocalDateTime registedAt, LocalDateTime updatedAt, boolean isAvailable, String profileUrl) {
        this.userId = userId;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.registedAt = registedAt;
        this.updatedAt = updatedAt;
        this.isAvailable = isAvailable;
        this.profileUrl = profileUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public LocalDateTime getRegistedAt() {
        return registedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNickname(String user_nickname) {
        this.userNickname = user_nickname;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRegistedAt(LocalDateTime registedAt) {
        this.registedAt = registedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", registedAt=" + registedAt +
                ", updatedAt=" + updatedAt +
                ", isAvailable=" + isAvailable +
                ", profileUrl='" + profileUrl + '\'' +
                '}';
    }
}
