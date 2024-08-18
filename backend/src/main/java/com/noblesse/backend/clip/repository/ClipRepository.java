package com.noblesse.backend.clip.repository;

import com.noblesse.backend.clip.domain.Clip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClipRepository extends JpaRepository<Clip, Long> {
    Clip findClipByClipId(Long clipId);

    @Transactional
    @Modifying
    @Query("UPDATE Clip c SET c.isOpened = CASE WHEN c.isOpened = true THEN false ELSE true END WHERE c.clipId = :clipId")
    void updateClipIsOpendByClipId(Long clipId);
}
