package com.noblesse.backend.clip.repository;

import com.noblesse.backend.clip.domain.ClipComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface ClipCommentRepository extends JpaRepository<ClipComment, Long> {
    ClipComment findClipCommentByClipCommentId(Long clipCommentId);

    @Transactional
    @Modifying
    @Query("UPDATE ClipComment c SET c.clipCommentContent = :clipCommentContent where c.clipCommentId = :clipCommentId")
    void updateClipComment(String clipCommentContent, Long clipCommentId);
}
