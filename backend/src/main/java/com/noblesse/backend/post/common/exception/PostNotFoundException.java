package com.noblesse.backend.post.common.exception;

public class PostNotFoundException extends IllegalArgumentException {
    public PostNotFoundException(Long id) {
        super(String.format("포스트 ID %d 를 찾을 수 없어요...", id));
    }
}
