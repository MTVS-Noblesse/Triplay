package com.noblesse.backend.post.repository;

import com.noblesse.backend.post.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    List<PostReport> findByUserId(Long userId);
}
