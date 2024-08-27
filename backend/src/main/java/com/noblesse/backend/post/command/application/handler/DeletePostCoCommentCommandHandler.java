package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.domain.publisher.PostCoCommentEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.dto.PostCoCommentDTO;
import com.noblesse.backend.post.common.entity.PostCoComment;
import com.noblesse.backend.post.common.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCoCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePostCoCommentCommandHandler {

    private final PostCoCommentRepository postCoCommentRepository;
    private final PostDomainService postDomainService;
    private final PostCoCommentEventPublisher postCoCommentEventPublisher;

    @Transactional
    public void handle(PostCoCommentDTO command) {
        // 1. 게시물 대댓글 조회
        PostCoComment postCoComment = postCoCommentRepository.findById(command.getPostCoCommentId())
                .orElseThrow(() -> new PostCommentNotFoundException(command.getPostCoCommentId()));

        // 2. 삭제 권한 확인
        if (postDomainService.canUserDeletePostCoComment(postCoComment, command.getUserId())) {
            throw new IllegalStateException(
                    String.format("User %d is not allowed to delete post co comment %d", command.getUserId(), command.getPostCoCommentId())
            );
        }

        // 3. 게시물 댓글 삭제
        postCoCommentRepository.delete(postCoComment);

        // 4. 삭제 이벤트 발행
        postCoCommentEventPublisher.publishPostCoCommentDeletedEvent(postCoComment);
    }
}
