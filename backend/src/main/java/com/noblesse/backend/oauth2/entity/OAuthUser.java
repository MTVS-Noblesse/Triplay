package com.noblesse.backend.oauth2.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user")
public class OAuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String email;
    private String provider; // ex: naver
    private String providerId; // 네이버에서 주는 고유 ID

    // 기본 생성자
    public OAuthUser() {}

    // 모든 필드를 포함하는 생성자
    public OAuthUser(String userName, String email, String provider, String providerId) {
        this.userName = userName;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
    }

    // Getter 및 Setter 메소드
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getUsername() {
        return userName;
    }

    public Long getUserId() {
        return id;
    }
}
