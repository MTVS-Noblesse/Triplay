package com.noblesse.backend.post.command.application.service;

import com.noblesse.backend.post.command.application.dto.*;
import com.noblesse.backend.post.command.domain.publisher.PostCommentEventPublisher;
import com.noblesse.backend.post.command.domain.publisher.PostEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.common.entity.PostComment;
import com.noblesse.backend.post.common.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.common.exception.PostNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCommentRepository;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommandService {
    /**
     * 1. 애플리케이션 레벨의 서비스로, 여러 도메인 서비스나
     *    레포지토리를 조합하여 사용함
     * 2. 트랜잭션 관리, 보안, 로깅 등 인프라스트럭처 관심사를 다룸
     * 3. Command Handler의 역할을 대신함
     */
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final PostDomainService postDomainService;
    private final PostEventPublisher postEventPublisher;
    private final PostCommentEventPublisher postCommentEventPublisher;

    /**
     * ### Post ###
     */
    public Long createPost(CreatePostCommandDTO createCommand) {
        Post newPost = Post.builder()
                .postTitle(createCommand.getPostTitle())
                .postContent(createCommand.getPostContent())
                .isOpened(createCommand.getIsOpened())
                .userId(createCommand.getUserId())
                .tripId(createCommand.getTripId())
                .clipId(createCommand.getClipId())
                .createdDateTime(createCommand.getCreatedDateTime())
                .updatedDateTime(createCommand.getUpdatedDateTime())
                .build();

        postDomainService.validatePostContent(newPost);

        Post savedPost = postRepository.save(newPost);
        postEventPublisher.publishPostCreatedEvent(savedPost);

        return savedPost.getPostId();
    }

    public void updatePost(UpdatePostCommandDTO updateCommand) {
        Post post = postRepository.findById(updateCommand.getPostId())
                .orElseThrow(() -> new PostNotFoundException(updateCommand.getPostId()));

        postDomainService.validatePostUpdate(post, updateCommand.getUserId());

        post.updatePost(updateCommand.getPostTitle(), updateCommand.getPostContent(), updateCommand.getIsOpened());
        postDomainService.validatePostContent(post);

        Post updatedPost = postRepository.save(post);
        postEventPublisher.publishPostUpdatedEvent(updatedPost);
    }

    public void deletePost(DeletePostCommandDTO deleteCommand) {
        Post post = postRepository.findById(deleteCommand.getPostId())
                .orElseThrow(() -> new PostNotFoundException(deleteCommand.getPostId()));

        if (postDomainService.canUserDeletePost(post, deleteCommand.getUserId())) {
            throw new IllegalStateException("# 포스트를 작성한 사용자가 일치하지 않아요...");
        }

        postRepository.delete(post);
        postEventPublisher.publishPostDeletedEvent(post);
    }

    /**
     * ### Post Comment ###
     */
    public Long createPostComment(CreatePostCommentCommandDTO createCommand) {
        PostComment newPostComment = PostComment.builder()
                .postCommentContent(createCommand.getPostCommentContent())
                .createdDateTime(createCommand.getCreatedDateTime())
                .updatedDateTime(createCommand.getUpdatedDateTime())
                .userId(createCommand.getUserId())
                .postId(createCommand.getPostId())
                .build();

        postDomainService.validatePostCommentContent(newPostComment);

        PostComment savedPostComment = postCommentRepository.save(newPostComment);
        postCommentEventPublisher.publishPostCommentCreatedEvent(savedPostComment);

        return savedPostComment.getPostId();
    }

    public void updatePostComment(UpdatePostCommentCommandDTO updateCommand) {
        PostComment postComment = postCommentRepository.findById(updateCommand.getPostCommentId())
                .orElseThrow(() -> new PostCommentNotFoundException(updateCommand.getPostCommentId()));

        postDomainService.validatePostCommentUpdate(postComment, updateCommand.getUserId());

        postComment.updatePostComment(updateCommand.getPostCommentContent());
        postDomainService.validatePostCommentContent(postComment);

        PostComment updatedPostComment = postCommentRepository.save(postComment);
        postCommentEventPublisher.publishPostCommentUpdatedEvent(updatedPostComment);
    }

    public void deletePostComment(DeletePostCommentCommandDTO deleteCommand) {
        PostComment postComment = postCommentRepository.findById(deleteCommand.getPostCommentId())
                .orElseThrow(() -> new PostCommentNotFoundException(deleteCommand.getPostCommentId()));

        if (postDomainService.canUserDeletePostComment(postComment, deleteCommand.getUserId())) {
            throw new IllegalStateException("# 포스트 댓글을 작성한 사용자가 일치하지 않아요...");
        }

        postCommentRepository.delete(postComment);
        postCommentEventPublisher.publishPostCommentDeletedEvent(postComment);
    }
}
