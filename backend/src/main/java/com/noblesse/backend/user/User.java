package com.noblesse.backend.user;

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
}
