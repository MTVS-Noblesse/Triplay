package com.noblesse.backend.post.query.infrastructure.persistence.repository;

import com.noblesse.backend.post.common.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findByUserId(Long userId);

    List<PostComment> findByPostId(Long postId);
}
