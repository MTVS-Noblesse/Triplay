package com.noblesse.backend.post.exception;

public class PostNotFoundException extends IllegalArgumentException {
    public PostNotFoundException(Long id) {
        super("# 포스트 고유 ID - " + id + "를 찾을 수 없어요...");
    }
}
