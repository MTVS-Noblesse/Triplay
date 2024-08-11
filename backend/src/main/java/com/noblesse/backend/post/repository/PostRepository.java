package com.noblesse.backend.post.repository;

import com.noblesse.backend.post.entity.Post;
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
