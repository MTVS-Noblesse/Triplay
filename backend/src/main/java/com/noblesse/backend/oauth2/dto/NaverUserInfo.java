package com.noblesse.backend.oauth2.dto;

import java.util.Map;

public class NaverUserInfo {
    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getProvider() {
        return "naver";
    }

    public String getProviderId() {
        return (String) attributes.get("id");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
