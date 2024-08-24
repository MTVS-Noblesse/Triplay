package com.noblesse.backend.clip.repository;

import com.noblesse.backend.clip.domain.ClipCoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClipCoCommentRepository extends JpaRepository<ClipCoComment, Long> {
    ClipCoComment findClipCoCommentByClipCoCommentId(Long clipCoCommentId);

    @Transactional
    @Modifying
    @Query("UPDATE ClipCoComment c SET c.clipCoCommentContent = :clipCoCommentContent WHERE c.clipCoCommentId = :clipCoCommentId")
    void updateClipCoCommentByClipCoCommentIdForContent(String ClipCoCommentContent, Long clipCoCommentId);
}
