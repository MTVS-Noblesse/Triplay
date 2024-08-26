package com.noblesse.backend.post.query.infrastructure.persistence.repository;

import com.noblesse.backend.post.common.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findByUserId(Long userId);

//    List<PostComment> findByPostId(Long postId);

    @Query("SELECT pc FROM PostComment pc WHERE pc.post.postId = :postId")
    List<PostComment> findByPostId(@Param("postId") Long postId);
}
