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
public class UpdatePostReportCommandHandler {

    private final PostReportRepository postReportRepository;
    private final PostDomainService postDomainService;
    private final PostReportEventPublisher postReportEventPublisher;

    @Transactional
    public void handle(PostReportDTO command) {
        PostReport postReport = postReportRepository.findById(command.getPostReportId())
                .orElseThrow(() -> new PostReportNotFoundException(command.getPostReportId()));

        postDomainService.validatePostReportUpdate(postReport, command.getUserId());

        postReport.updatePostReport(command.getPostReportContent());
        postDomainService.validatePostReportContent(postReport);

        PostReport updatedPostReport = postReportRepository.save(postReport);
        postReportEventPublisher.publishPostReportUpdatedEvent(updatedPostReport);
    }
}
