package com.noblesse.backend.post.exception;

public class PostReportNotFoundException extends IllegalArgumentException {
    public PostReportNotFoundException(Long id) {
        super("# 포스트 신고 고유 ID - " + id + "를 찾을 수 없어요...");
    }
}
