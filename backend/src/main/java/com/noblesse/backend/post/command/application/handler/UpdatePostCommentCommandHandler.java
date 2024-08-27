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
public class UpdatePostCommentCommandHandler {

    private final PostCommentRepository postCommentRepository;
    private final PostDomainService postDomainService;
    private final PostCommentEventPublisher postCommentEventPublisher;

    @Transactional
    public void handle(PostCommentDTO command) {
        PostComment postComment = postCommentRepository.findById(command.getPostCommentId())
                .orElseThrow(() -> new PostCommentNotFoundException(command.getPostCommentId()));

        postDomainService.validatePostCommentUpdate(postComment, command.getUserId());
        postComment.updatePostComment(command.getPostCommentContent());

        PostComment updatedPostComment = postCommentRepository.save(postComment);
        postCommentEventPublisher.publishPostCommentUpdatedEvent(updatedPostComment);
    }
}
