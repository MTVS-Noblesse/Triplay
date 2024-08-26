package com.noblesse.backend.oauth2.security;

import com.noblesse.backend.oauth2.entity.OAuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {
    private OAuthUser user; // 사용자 정보
    private Map<String, Object> attributes; // OAuth2 사용자 정보

    public PrincipalDetails(OAuthUser user) {
        this.user = user;
    }

    public PrincipalDetails(OAuthUser user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 필요에 따라 구현
    }

    @Override
    public String getPassword() {
        return null; // 비밀번호가 없으므로 null 반환
    }

    @Override
    public String getUsername() {
        return user.getUserName(); // 사용자 이름 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes; // OAuth2 사용자 정보 반환
    }

    @Override
    public String getName() {
        return user.getUserName(); // 사용자 이름 반환
    }

    public OAuthUser getUser() {
        return user;
    }
}