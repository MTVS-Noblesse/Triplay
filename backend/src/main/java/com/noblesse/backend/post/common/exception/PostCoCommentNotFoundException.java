package com.noblesse.backend.post.common.exception;

public class PostCoCommentNotFoundException extends IllegalArgumentException {
    public PostCoCommentNotFoundException(Long id) {
        super(String.format("포스트 대댓글 ID %d 를 찾을 수 없어요...", id));
    }
}
