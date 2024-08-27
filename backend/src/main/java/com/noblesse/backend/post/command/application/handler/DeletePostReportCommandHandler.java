package com.noblesse.backend.post.command.application.handler;

import com.noblesse.backend.post.command.domain.publisher.PostReportEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.dto.PostReportDTO;
import com.noblesse.backend.post.common.entity.PostReport;
import com.noblesse.backend.post.common.exception.PostReportNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostReportRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeletePostReportCommandHandler {

    private final PostReportRepository postReportRepository;
    private final PostDomainService postDomainService;
    private final PostReportEventPublisher postReportEventPublisher;

    @Transactional
    public void handle(PostReportDTO command) {
        // 1. 게시물 신고글 조회
        PostReport postReport = postReportRepository.findById(command.getPostReportId())
                .orElseThrow(() -> new PostReportNotFoundException(command.getPostReportId()));

        // 2. 삭제 권한 확인
        if (postDomainService.canUserDeletePostReport(postReport, command.getUserId())) {
            throw new IllegalStateException(
                    String.format("User %d is not allowed to delete post report %d", command.getUserId(), command.getPostReportId())
            );
        }

        // 3. 게시물 신고글 삭제
        postReportRepository.delete(postReport);

        // 4. 삭제 이벤트 발행
        postReportEventPublisher.publishPostReportDeletedEvent(postReport);
    }
}
