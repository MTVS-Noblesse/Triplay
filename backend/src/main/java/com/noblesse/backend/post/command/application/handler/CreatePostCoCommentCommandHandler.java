package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.domain.publisher.PostCoCommentEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.dto.PostCoCommentDTO;
import com.noblesse.backend.post.common.dto.PostDTO;
import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.common.entity.PostCoComment;
import com.noblesse.backend.post.common.entity.PostComment;
import com.noblesse.backend.post.common.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCoCommentRepository;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreatePostCoCommentCommandHandler {

    private final PostCoCommentRepository postCoCommentRepository;
    private final PostDomainService postDomainService;
    private final PostCoCommentEventPublisher postCoCommentEventPublisher;

    @Transactional
    public Long handle(PostCoCommentDTO command) {
        // 1. 입력값 유효성 검증
        validateCreatePostCoCommentCommand(command);

        // 2. PostCoComment 엔티티 생성
        PostCoComment newPostCoComment = createPostCoCommentFromCommand(command);

        // 3. 비즈니스 로직 적용
        postDomainService.validatePostCoCommentContent(newPostCoComment);

        // 4. 저장소에 저장
        PostCoComment savedPostCoComment = postCoCommentRepository.save(newPostCoComment);

        // 5. 이벤트 발행
        postCoCommentEventPublisher.publishPostCoCommentCreatedEvent(savedPostCoComment);

        return savedPostCoComment.getPostCoCommentId();
    }

    private void validateCreatePostCoCommentCommand(PostCoCommentDTO command) {
        if (command.getPostCoCommentContent() == null || command.getPostCoCommentContent().trim().isEmpty()) {
            throw new IllegalArgumentException("포스트 대댓글은 공백일 수 없어요...");
        }

        if (command.getUserId() == null) {
            throw new IllegalArgumentException("사용자 ID가 지정되어야 해요...");
        }
    }

    private PostCoComment createPostCoCommentFromCommand(PostCoCommentDTO command) {
        return PostCoComment.builder()
                .postCoCommentContent(command.getPostCoCommentContent())
                .writtenDatetime(LocalDateTime.now())
                .modifiedDatetime(LocalDateTime.now())
                .userId(command.getUserId())
                .postCommentId(command.getPostCommentId())
                .build();
    }
}
