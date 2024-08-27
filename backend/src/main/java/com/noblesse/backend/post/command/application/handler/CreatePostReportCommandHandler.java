package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.domain.publisher.PostReportEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.dto.PostCommentDTO;
import com.noblesse.backend.post.common.dto.PostReportDTO;
import com.noblesse.backend.post.common.entity.PostComment;
import com.noblesse.backend.post.common.entity.PostReport;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreatePostReportCommandHandler {

    private final PostReportRepository postReportRepository;
    private final PostDomainService postDomainService;
    private final PostReportEventPublisher postReportEventPublisher;

    @Transactional
    public Long handle(PostReportDTO command) {
        // 1. 입력값 유효성 검증
        validateCreatePostReportCommand(command);

        // 2. PostReport 엔티티 생성
        PostReport newPostReport = createPostReportFromCommand(command);

        // 3. 비즈니스 로직 적용
        postDomainService.validatePostReportContent(newPostReport);

        // 4. 저장소에 저장
        PostReport savedPostReport = postReportRepository.save(newPostReport);

        // 5. 이벤트 발행
        postReportEventPublisher.publishPostReportCreatedEvent(savedPostReport);

        return savedPostReport.getPostReportId();
    }

    private void validateCreatePostReportCommand(PostReportDTO command) {
        if (command.getPostReportContent() == null || command.getPostReportContent().trim().isEmpty()) {
            throw new IllegalArgumentException("포스트 신고글은 공백일 수 없어요...");
        }

        if (command.getUserId() == null) {
            throw new IllegalArgumentException("사용자 ID가 지정되어야 해요...");
        }
    }

    private PostReport createPostReportFromCommand(PostReportDTO command) {
        return PostReport.builder()
                .postReportContent(command.getPostReportContent())
                .isReported(command.getIsReported())
                .createdDatetime(LocalDateTime.now())
                .processedDatetime(LocalDateTime.now())
                .reportCategoryId(command.getReportCategoryId())
                .userId(command.getUserId())
                .postId(command.getPostId())
                .build();
    }
}
