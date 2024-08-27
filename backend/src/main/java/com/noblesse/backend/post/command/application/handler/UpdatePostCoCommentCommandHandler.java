package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.domain.publisher.PostCoCommentEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.dto.PostCoCommentDTO;
import com.noblesse.backend.post.common.entity.PostCoComment;
import com.noblesse.backend.post.common.exception.PostCoCommentNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCoCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatePostCoCommentCommandHandler {

    private final PostCoCommentRepository postCoCommentRepository;
    private final PostDomainService postDomainService;
    private final PostCoCommentEventPublisher postCoCommentEventPublisher;

    @Transactional
    public void handle(PostCoCommentDTO command) {
        PostCoComment postCoComment = postCoCommentRepository.findById(command.getPostCoCommentId())
                .orElseThrow(() -> new PostCoCommentNotFoundException(command.getPostCoCommentId()));

        postDomainService.validatePostCoCommentUpdate(postCoComment, command.getUserId());
        postCoComment.updatePostCoComment(command.getPostCoCommentContent());

        PostCoComment updatedPostCoComment = postCoCommentRepository.save(postCoComment);
        postCoCommentEventPublisher.publishPostCoCommentUpdatedEvent(updatedPostCoComment);
    }
}
