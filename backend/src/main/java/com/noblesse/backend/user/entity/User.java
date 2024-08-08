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
    private String user_nickname;

    @Column(name="user_email")
    private String user_email;

    @Column(name="registed_at")
    private LocalDateTime registed_at;

    @Column(name="updated_at")
    private LocalDateTime updated_at;

    @Column(name="is_available")
    private boolean is_available;

    @Column(name="profile_url")
    private String profile_url;

    public User() {
    }

    public User(Long userId, String userName, String user_nickname, String user_email, LocalDateTime registed_at, LocalDateTime updated_at, boolean is_available, String profile_url) {
        this.userId = userId;
        this.userName = userName;
        this.user_nickname = user_nickname;
        this.user_email = user_email;
        this.registed_at = registed_at;
        this.updated_at = updated_at;
        this.is_available = is_available;
        this.profile_url = profile_url;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getUser_email() {
        return user_email;
    }

    public LocalDateTime getRegisted_at() {
        return registed_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public boolean isIs_available() {
        return is_available;
    }

    public String getProfile_url() {
        return profile_url;
    }
}
