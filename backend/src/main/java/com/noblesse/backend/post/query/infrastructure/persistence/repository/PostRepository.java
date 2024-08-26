package com.noblesse.backend.post.query.infrastructure.persistence.repository;

import com.noblesse.backend.post.common.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);

    List<Post> findByCreatedDateTimeBetweenAndUserIdInAndIsOpenedTrue(
            LocalDateTime startDate,
            LocalDateTime endDate,
            List<Long> userIds
    );
}
