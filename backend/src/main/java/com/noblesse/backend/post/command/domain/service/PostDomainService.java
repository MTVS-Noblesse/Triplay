package com.noblesse.backend.post.command.domain.service;

import com.noblesse.backend.post.common.entity.Post;
import org.springframework.stereotype.Service;

@Service
public class PostDomainService {
    /**
     * 1. 도메인 로직에 집중하기 위한 클래스 선언
     * 2. 특정 기술(API, DB 등)에 의존하지 않음
     * 3. Post 엔티티의 비즈니스 규칙을 이곳에서 구현함
     */
    public void validatePostContent(Post post) {
        if (post.getPostContent().length() < 10) {
            throw new IllegalArgumentException("포스트 본문은 최소 10자 이상으로 작성해 주세요...");
        }

        if (post.getPostTitle().length() < 3) {
            throw new IllegalArgumentException("포스트 제목은 최소 3자 이상으로 작성해 주세요...");
        }
    }

    public void validatePostUpdate(Post post, Long userId) {
        if (!post.getUserId().equals(userId)) {
            throw new IllegalStateException("포스트를 작성한 사용자가 일치하지 않아요...");
        }
    }

    public boolean canUserDeletePost(Post post, Long userId) {
        return !post.getUserId().equals(userId);
    }
}
