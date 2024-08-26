package com.noblesse.backend.post.common.exception;

public class PostCommentNotFoundException extends IllegalArgumentException {
    public PostCommentNotFoundException(Long id) {
        super(String.format("포스트 댓글 ID %d 를 찾을 수 없어요...", id));
    }
}
