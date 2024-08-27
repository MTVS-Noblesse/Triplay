package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.domain.publisher.PostCommentEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.dto.PostCommentDTO;
import com.noblesse.backend.post.common.entity.PostComment;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreatePostCommentCommandHandler {

    private final PostCommentRepository postCommentRepository;
    private final PostDomainService postDomainService;
    private final PostCommentEventPublisher postCommentEventPublisher;

    @Transactional
    public Long handle(PostCommentDTO command) {
        // 1. 입력값 유효성 검증
        validateCreatePostCommentCommand(command);

        // 2. PostComment 엔티티 생성
        PostComment newPostComment = createPostCommentFromCommand(command);

        // 3. 비즈니스 로직 적용
        postDomainService.validatePostCommentContent(newPostComment);

        // 4. 저장소에 저장
        PostComment savedPostComment = postCommentRepository.save(newPostComment);

        // 5. 이벤트 발행
        postCommentEventPublisher.publishPostCommentCreatedEvent(savedPostComment);

        return savedPostComment.getPostCommentId();
    }

    private void validateCreatePostCommentCommand(PostCommentDTO command) {
        if (command.getPostCommentContent() == null || command.getPostCommentContent().trim().isEmpty()) {
            throw new IllegalArgumentException("포스트 댓글은 공백일 수 없어요...");
        }

        if (command.getUserId() == null) {
            throw new IllegalArgumentException("사용자 ID가 지정되어야 해요...");
        }
    }

    private PostComment createPostCommentFromCommand(PostCommentDTO command) {
        return PostComment.builder()
                .postCommentContent(command.getPostCommentContent())
                .writtenDatetime(LocalDateTime.now())
                .modifiedDatetime(LocalDateTime.now())
                .userId(command.getUserId())
                .postId(command.getPostId())
                .build();
    }
}
