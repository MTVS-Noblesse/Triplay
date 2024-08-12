package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.application.dto.DeletePostCommandDTO;
import com.noblesse.backend.post.command.domain.publisher.PostEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.common.exception.PostNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePostCommandHandler {

    private final PostRepository postRepository;
    private final PostDomainService postDomainService;
    private final PostEventPublisher postEventPublisher;

    @Transactional
    public void handle(DeletePostCommandDTO command) {
        // 1. 게시물 조회
        Post post = postRepository.findById(command.getPostId())
                .orElseThrow(() -> new PostNotFoundException(command.getPostId()));

        // 2. 삭제 권한 확인
        if (postDomainService.canUserDeletePost(post, command.getUserId())) {
            throw new IllegalStateException(
                    String.format("User %d is not allowed to delete post %d", command.getUserId(), command.getPostId())
            );
        }

        // 3. 게시물 삭제
        postRepository.delete(post);

        // 4. 삭제 이벤트 발행
        postEventPublisher.publishPostDeletedEvent(post);
    }
}
