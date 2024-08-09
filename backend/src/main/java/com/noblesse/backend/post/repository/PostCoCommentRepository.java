package com.noblesse.backend.post.repository;

import com.noblesse.backend.post.entity.PostCoComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCoCommentRepository extends JpaRepository<PostCoComment, Long> {

    List<PostCoComment> findByUserId(Long userId);
}
