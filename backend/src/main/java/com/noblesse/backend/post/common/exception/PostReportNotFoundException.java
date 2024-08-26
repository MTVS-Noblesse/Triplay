package com.noblesse.backend.post.common.exception;

public class PostReportNotFoundException extends IllegalArgumentException {
    public PostReportNotFoundException(Long id) {
        super(String.format("포스트 신고 ID %d 를 찾을 수 없어요...", id));
    }
}
