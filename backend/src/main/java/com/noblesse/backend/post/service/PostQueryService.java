package com.noblesse.backend.post.service;

import com.noblesse.backend.post.dto.query.PostCommentDTO;
import com.noblesse.backend.post.dto.query.PostDTO;
import com.noblesse.backend.post.entity.Post;
import com.noblesse.backend.post.entity.PostCoComment;
import com.noblesse.backend.post.entity.PostComment;
import com.noblesse.backend.post.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.exception.PostNotFoundException;
import com.noblesse.backend.post.repository.PostCommentRepository;
import com.noblesse.backend.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostQueryService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public PostQueryService(PostRepository postRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
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

    /** Pageable을 통해 모든 포스트를 조회하는 메서드 */
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(PostDTO::new);
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

    /** 사용자 고유 ID(userId)로 해당 사용자의 모든 포스트 댓글을 조회하는 메서드 */
    public List<PostCommentDTO> getPostCommentsByUserId(Long userId) {
        List<PostComment> postComments = postCommentRepository.findByUserId(userId);
        return postComments.stream()
                .map(PostCommentDTO::new)
                .collect(Collectors.toList());
    }

    /** Pageable을 통해 모든 포스트 댓글을 조회하는 메서드 */
    public Page<PostCommentDTO> getAllPostComments(Pageable pageable) {
        Page<PostComment> postCommentPage = postCommentRepository.findAll(pageable);
        return postCommentPage.map(PostCommentDTO::new);
    }
}
