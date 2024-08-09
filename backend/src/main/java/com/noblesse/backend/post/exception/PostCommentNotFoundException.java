package com.noblesse.backend.post.exception;

public class PostCommentNotFoundException extends IllegalArgumentException {
    public PostCommentNotFoundException(Long id) {
        super("# 포스트 댓글 고유 ID - " + id + "를 찾을 수 없어요...");
    }
}
