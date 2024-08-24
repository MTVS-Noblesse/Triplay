package com.noblesse.backend.clip.service;

import com.noblesse.backend.clip.domain.Clip;
import com.noblesse.backend.clip.domain.ClipReport;
import com.noblesse.backend.clip.dto.ClipReportRegistRequestDTO;
import com.noblesse.backend.clip.repository.ClipReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClipReportService {
    private final ClipReportRepository clipReportRepository;

    public ClipReportService(ClipReportRepository clipReportRepository) {
        this.clipReportRepository = clipReportRepository;
    }

    public ClipReport findClipByClipId(Long ClipId) {
        return clipReportRepository.findClipReportByClipId(ClipId);
    }

    public List<ClipReport> findAll() {
        return clipReportRepository.findAll();
    }

    public void registClipReport(ClipReportRegistRequestDTO clipReportRegistRequestDTO) {
        clipReportRepository.save(new ClipReport(
                clipReportRegistRequestDTO.getReportCategoryId(),
                clipReportRegistRequestDTO.getClipReportTitle(),
                clipReportRegistRequestDTO.getClipReportContent(),
                clipReportRegistRequestDTO.getUserId(),
                clipReportRegistRequestDTO.getClipId()
        ));
    }

    // UPDATE 문은 따로 필요 없을 듯, 신고 내용을 수정하는 정도에서는 제목, 내용 정도..?
    @Transactional
    public void updateClipReport(Long ClipReportId, String newTitle, String newContent) {
        clipReportRepository.updateClipReportByClipReportId(ClipReportId, newTitle, newContent);
    }

    @Transactional
    public void deleteClipByClipId(Long ClipId) {
        clipReportRepository.deleteById(ClipId);
    }
}
