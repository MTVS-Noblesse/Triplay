package com.noblesse.backend.post.query.application.service;

import com.noblesse.backend.post.common.dto.PostCoCommentDTO;
import com.noblesse.backend.post.common.dto.PostCommentDTO;
import com.noblesse.backend.post.common.dto.PostDTO;
import com.noblesse.backend.post.common.dto.PostReportDTO;
import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.common.entity.PostCoComment;
import com.noblesse.backend.post.common.entity.PostComment;
import com.noblesse.backend.post.common.entity.PostReport;
import com.noblesse.backend.post.common.exception.PostCoCommentNotFoundException;
import com.noblesse.backend.post.common.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.common.exception.PostNotFoundException;
import com.noblesse.backend.post.common.exception.PostReportNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCoCommentRepository;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCommentRepository;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostReportRepository;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostQueryService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostCoCommentRepository postCoCommentRepository;
    private final PostReportRepository postReportRepository;

    public PostQueryService(PostRepository postRepository, PostCommentRepository postCommentRepository, PostCoCommentRepository postCoCommentRepository, PostReportRepository postReportRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
        this.postCoCommentRepository = postCoCommentRepository;
        this.postReportRepository = postReportRepository;
    }

    /**
     * ### PostDTO ###
     */
    /** 포스트 고유 ID로 포스팅 검색하는 메서드 */
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        return new PostDTO(post);
    }

    /** 사용자 고유 ID(userId)로 해당 사용자의 모든 포스트를 조회하는 메서드 */
    public List<PostDTO> getPostsByUserId(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        return posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    /** 모든 포스트를 조회하는 메서드 */
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    /** 복잡한 조건의 게시물을 검색하는 메서드 */
    public List<PostDTO> searchPosts(LocalDateTime startDate, LocalDateTime endDate, List<Long> userIds, boolean isOpened) {
        List<Post> posts = postRepository.findByWrittenDatetimeBetweenAndUserIdInAndIsOpened(startDate, endDate, userIds, isOpened);
        return posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * ### PostCommentDTO ###
     */
    /** 포스트 댓글 고유 ID로 포스팅 댓글을 검색하는 메서드 */
    public PostCommentDTO getPostCommentById(Long id) {
        PostComment comment = postCommentRepository.findById(id)
                .orElseThrow(() -> new PostCommentNotFoundException(id));
        return new PostCommentDTO(comment);
    }

    /** 포스트 고유 ID(postId)로 포스트 댓글 전체를 조회하는 메서드 */
    public List<PostCommentDTO> getPostCommentsByPostId(Long postId) {
        List<PostComment> postComments = postCommentRepository.findByPostId(postId);
        return postComments.stream()
                .map(PostCommentDTO::new)
                .collect(Collectors.toList());
    }

    /** 모든 포스트 댓글을 조회하는 메서드 */
    public List<PostCommentDTO> getAllPostComments() {
        List<PostComment> postComments = postCommentRepository.findAll();

        return postComments.stream()
                .map(PostCommentDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * ### PostCoCommentDTO ###
     */
    /** 포스트 댓글 고유 ID로 포스팅 대댓글을 검색하는 메서드 */
    public PostCoCommentDTO getPostCoCommentById(Long id) {
        PostCoComment coComment = postCoCommentRepository.findById(id)
                .orElseThrow(() -> new PostCoCommentNotFoundException(id));
        return new PostCoCommentDTO(coComment);
    }

    /** 포스트 댓글 고유 ID(userId)로 해당 사용자의 모든 포스트 대댓글을 조회하는 메서드 */
    public List<PostCoCommentDTO> getPostCoCommentsByPostCommentId(Long postCommentId) {

        List<PostCoComment> postCoComments = postCoCommentRepository.findByPostCommentId(postCommentId);
        return postCoComments.stream()
                .map(PostCoCommentDTO::new)
                .collect(Collectors.toList());
    }

    /** 모든 포스트 대댓글을 조회하는 메서드 */
    public List<PostCoCommentDTO> getAllPostCoComments() {
        List<PostCoComment> postCoComments = postCoCommentRepository.findAll();

        return postCoComments.stream()
                .map(PostCoCommentDTO::new)
                .collect(Collectors.toList());
    }

    /** 포스트 댓글을 통해 포스트 대댓글을 조회하는 메서드 */
    private List<PostCoCommentDTO> getCoCommentsForComment(Long commentId) {
        List<PostCoComment> coComments = postCoCommentRepository.findByPostCommentId(commentId);
        return coComments.stream()
                .map(PostCoCommentDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * ### PostReportDTO ###
     */
    /** 포스트 신고 고유 ID로 포스팅 신고를 검색하는 메서드 */
    public PostReportDTO getPostReportById(Long id) {
        PostReport postReport = postReportRepository.findById(id)
                .orElseThrow(() -> new PostReportNotFoundException(id));
        return new PostReportDTO(postReport);
    }

    /** 사용자 고유 ID(userId)로 해당 사용자의 모든 포스트 신고를 조회하는 메서드 */
    public List<PostReportDTO> getPostReportsByUserId(Long userId) {
        List<PostReport> postReports = postReportRepository.findByUserId(userId);
        return postReports.stream()
                .map(PostReportDTO::new)
                .collect(Collectors.toList());
    }

    /** 특정 포스트에 대한 모든 포스트 신고를 조회하는 메서드 */
    public List<PostReportDTO> getPostReportsByPostId(Long postId) {
        List<PostReport> postReports = postReportRepository.findByPostId(postId);
        return postReports.stream()
                .map(PostReportDTO::new)
                .collect(Collectors.toList());
    }

    /** 모든 포스트 신고를 조회하는 메서드 */
    public List<PostReportDTO> getAllPostReports() {
        List<PostReport> postReports = postReportRepository.findAll();

        return postReports.stream()
                .map(PostReportDTO::new)
                .collect(Collectors.toList());
    }

    /** 처리되지 않은 포스트 신고를 조회하는 메서드 */
    public List<PostReportDTO> getUnprocessedPostReports() {
        List<PostReport> unprocessedReports = postReportRepository.findByIsReportedFalse();
        return unprocessedReports.stream()
                .map(PostReportDTO::new)
                .collect(Collectors.toList());
    }
}
