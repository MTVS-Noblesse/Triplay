package com.noblesse.backend.clip.repository;

import com.noblesse.backend.clip.domain.ClipReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClipReportRepository extends JpaRepository<ClipReport, Long> {
    ClipReport findClipReportByClipId(Long clipId);

    @Transactional
    @Modifying
    @Query("UPDATE ClipReport c SET c.clipReportTitle = :newReportTitle, c.clipReportContent = :newReportContent WHERE c.clipReportId = :clipReportId")
    void updateClipReportByClipReportId(Long clipReportId, String newReportTitle, String newReportContent);
}
