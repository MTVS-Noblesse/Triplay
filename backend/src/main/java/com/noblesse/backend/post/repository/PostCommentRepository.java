package com.noblesse.backend.post.repository;

import com.noblesse.backend.post.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findByUserId(Long userId);

    List<PostComment> findByPostId(Long postId);
}
