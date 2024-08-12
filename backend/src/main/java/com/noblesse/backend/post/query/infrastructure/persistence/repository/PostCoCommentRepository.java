package com.noblesse.backend.post.query.infrastructure.persistence.repository;

import com.noblesse.backend.post.common.entity.PostCoComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCoCommentRepository extends JpaRepository<PostCoComment, Long> {

    List<PostCoComment> findByUserId(Long userId);

    List<PostCoComment> findByPostCommentId(Long postCommentId);
}
