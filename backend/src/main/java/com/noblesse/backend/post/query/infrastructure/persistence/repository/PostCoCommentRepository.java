package com.noblesse.backend.post.query.infrastructure.persistence.repository;

import com.noblesse.backend.post.common.entity.PostCoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostCoCommentRepository extends JpaRepository<PostCoComment, Long> {

    List<PostCoComment> findByUserId(Long userId);

    List<PostCoComment> findByPostCoCommentId(Long postCoCommentId);

    @Query("SELECT pcc FROM PostCoComment pcc WHERE pcc.postComment.postCommentId = :postCommentId")
    List<PostCoComment> findByPostCommentId(@Param("postCommentId") Long postCommentId);

//    List<PostCoComment> findByPostCommentId(Long postCommentId);
}
