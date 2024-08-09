package com.noblesse.backend.post.exception;

public class PostCoCommentNotFoundException extends IllegalArgumentException {
    public PostCoCommentNotFoundException(Long id) {
        super("# 포스트 대댓글 고유 ID - " + id + "를 찾을 수 없어요...");
    }
}
