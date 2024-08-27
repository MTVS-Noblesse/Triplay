package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.domain.publisher.PostCommentEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.dto.PostCommentDTO;
import com.noblesse.backend.post.common.entity.PostComment;
import com.noblesse.backend.post.common.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePostCommentCommandHandler {

    private final PostCommentRepository postCommentRepository;
    private final PostDomainService postDomainService;
    private final PostCommentEventPublisher postCommentEventPublisher;

    @Transactional
    public void handle(PostCommentDTO command) {
        // 1. 게시물 댓글 조회
        PostComment postComment = postCommentRepository.findById(command.getPostCommentId())
                .orElseThrow(() -> new PostCommentNotFoundException(command.getPostCommentId()));

        // 2. 삭제 권한 확인
        if (postDomainService.canUserDeletePostComment(postComment, command.getUserId())) {
            throw new IllegalStateException(
                    String.format("User %d is not allowed to delete post comment %d", command.getUserId(), command.getPostCommentId())
            );
        }

        // 3. 게시물 댓글 삭제
        postCommentRepository.delete(postComment);

        // 4. 삭제 이벤트 발행
        postCommentEventPublisher.publishPostCommentDeletedEvent(postComment);
    }
}
