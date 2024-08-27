package com.noblesse.backend.post.command.application.service;

import com.noblesse.backend.post.command.domain.publisher.PostCoCommentEventPublisher;
import com.noblesse.backend.post.command.domain.publisher.PostCommentEventPublisher;
import com.noblesse.backend.post.command.domain.publisher.PostEventPublisher;
import com.noblesse.backend.post.command.domain.publisher.PostReportEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
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
    private final PostCoCommentRepository postCoCommentRepository;
    private final PostReportRepository postReportRepository;
    private final PostDomainService postDomainService;
    private final PostEventPublisher postEventPublisher;
    private final PostCommentEventPublisher postCommentEventPublisher;
    private final PostCoCommentEventPublisher postCoCommentEventPublisher;
    private final PostReportEventPublisher postReportEventPublisher;

    /**
     * ### Post ###
     */
    public Long createPost(PostDTO createCommand) {
        Post newPost = Post.builder()
                .postTitle(createCommand.getPostTitle())
                .postContent(createCommand.getPostContent())
                .isOpened(createCommand.getIsOpened())
                .userId(createCommand.getUserId())
                .tripId(createCommand.getTripId())
                .clipId(createCommand.getClipId())
                .writtenDatetime(createCommand.getWrittenDatetime())
                .modifiedDatetime(createCommand.getModifiedDatetime())
                .build();

        postDomainService.validatePostContent(newPost);

        Post savedPost = postRepository.save(newPost);
        postEventPublisher.publishPostCreatedEvent(savedPost);

        return savedPost.getPostId();
    }

    public void updatePost(PostDTO updateCommand) {
        Post post = postRepository.findById(updateCommand.getPostId())
                .orElseThrow(() -> new PostNotFoundException(updateCommand.getPostId()));

        postDomainService.validatePostUpdate(post, updateCommand.getUserId());

        post.updatePost(updateCommand.getPostTitle(), updateCommand.getPostContent(), updateCommand.getIsOpened());
        postDomainService.validatePostContent(post);

        Post updatedPost = postRepository.save(post);
        postEventPublisher.publishPostUpdatedEvent(updatedPost);
    }

    public void deletePost(PostDTO deleteCommand) {
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
    public Long createPostComment(PostCommentDTO createCommand) {
        PostComment newPostComment = PostComment.builder()
                .postCommentContent(createCommand.getPostCommentContent())
                .writtenDatetime(createCommand.getWrittenDatetime())
                .modifiedDatetime(createCommand.getModifiedDatetime())
                .userId(createCommand.getUserId())
                .postId(createCommand.getPostId())
                .build();

        postDomainService.validatePostCommentContent(newPostComment);

        PostComment savedPostComment = postCommentRepository.save(newPostComment);
        postCommentEventPublisher.publishPostCommentCreatedEvent(savedPostComment);

        return savedPostComment.getPostCommentId();
    }

    public void updatePostComment(PostCommentDTO updateCommand) {
        PostComment postComment = postCommentRepository.findById(updateCommand.getPostCommentId())
                .orElseThrow(() -> new PostCommentNotFoundException(updateCommand.getPostCommentId()));

        postDomainService.validatePostCommentUpdate(postComment, updateCommand.getUserId());

        postComment.updatePostComment(updateCommand.getPostCommentContent());
        postDomainService.validatePostCommentContent(postComment);

        PostComment updatedPostComment = postCommentRepository.save(postComment);
        postCommentEventPublisher.publishPostCommentUpdatedEvent(updatedPostComment);
    }

    public void deletePostComment(PostCommentDTO deleteCommand) {
        PostComment postComment = postCommentRepository.findById(deleteCommand.getPostCommentId())
                .orElseThrow(() -> new PostCommentNotFoundException(deleteCommand.getPostCommentId()));

        if (postDomainService.canUserDeletePostComment(postComment, deleteCommand.getUserId())) {
            throw new IllegalStateException("# 포스트 댓글을 작성한 사용자가 일치하지 않아요...");
        }

        postCommentRepository.delete(postComment);
        postCommentEventPublisher.publishPostCommentDeletedEvent(postComment);
    }

    /**
     * ### Post Co Comment ###
     */
    public Long createPostCoComment(PostCoCommentDTO createCommand) {
        PostCoComment newPostCoComment = PostCoComment.builder()
                .postCoCommentContent(createCommand.getPostCoCommentContent())
                .writtenDatetime(createCommand.getWrittenDatetime())
                .modifiedDatetime(createCommand.getModifiedDatetime())
                .userId(createCommand.getUserId())
                .postCommentId(createCommand.getPostCommentId())
                .build();

        postDomainService.validatePostCoCommentContent(newPostCoComment);

        PostCoComment savedPostCoComment = postCoCommentRepository.save(newPostCoComment);
        postCoCommentEventPublisher.publishPostCoCommentCreatedEvent(savedPostCoComment);

        return savedPostCoComment.getPostCoCommentId();
    }

    public void updatePostComment(PostCoCommentDTO updateCommand) {
        PostCoComment postCoComment = postCoCommentRepository.findById(updateCommand.getPostCoCommentId())
                .orElseThrow(() -> new PostCoCommentNotFoundException(updateCommand.getPostCoCommentId()));

        postDomainService.validatePostCoCommentUpdate(postCoComment, updateCommand.getUserId());

        postCoComment.updatePostCoComment(updateCommand.getPostCoCommentContent());
        postDomainService.validatePostCoCommentContent(postCoComment);

        PostCoComment updatedPostCoComment = postCoCommentRepository.save(postCoComment);
        postCoCommentEventPublisher.publishPostCoCommentUpdatedEvent(updatedPostCoComment);
    }

    public void deletePostComment(PostCoCommentDTO deleteCommand) {
        PostCoComment postCoComment = postCoCommentRepository.findById(deleteCommand.getPostCoCommentId())
                .orElseThrow(() -> new PostCoCommentNotFoundException(deleteCommand.getPostCoCommentId()));

        if (postDomainService.canUserDeletePostCoComment(postCoComment, deleteCommand.getUserId())) {
            throw new IllegalStateException("# 포스트 대댓글을 작성한 사용자가 일치하지 않아요...");
        }

        postCoCommentRepository.delete(postCoComment);
        postCoCommentEventPublisher.publishPostCoCommentDeletedEvent(postCoComment);
    }

    /**
     * ### Post Report ###
     */
    public Long createPostReport(PostReportDTO createCommand) {
        PostReport newPostReport = PostReport.builder()
                .postReportContent(createCommand.getPostReportContent())
                .isReported(createCommand.getIsReported())
                .createdDatetime(createCommand.getCreatedDatetime())
                .processedDatetime(createCommand.getProcessedDatetime())
                .reportCategoryId(createCommand.getReportCategoryId())
                .userId(createCommand.getUserId())
                .postId(createCommand.getPostId())
                .build();

        postDomainService.validatePostReportContent(newPostReport);

        PostReport savedPostReport = postReportRepository.save(newPostReport);
        postReportEventPublisher.publishPostReportCreatedEvent(savedPostReport);

        return savedPostReport.getPostReportId();
    }

    public void updatePostReport(PostReportDTO updateCommand) {
        PostReport postReport = postReportRepository.findById(updateCommand.getPostReportId())
                .orElseThrow(() -> new PostReportNotFoundException(updateCommand.getPostReportId()));

        postDomainService.validatePostReportUpdate(postReport, updateCommand.getUserId());

        postReport.updatePostReport(updateCommand.getPostReportContent());
        postDomainService.validatePostReportContent(postReport);

        PostReport updatedPostReport = postReportRepository.save(postReport);
        postReportEventPublisher.publishPostReportUpdatedEvent(updatedPostReport);
    }

    public void deletePostComment(PostReportDTO deleteCommand) {
        PostReport postReport = postReportRepository.findById(deleteCommand.getPostReportId())
                .orElseThrow(() -> new PostReportNotFoundException(deleteCommand.getPostReportId()));

        if (postDomainService.canUserDeletePostReport(postReport, deleteCommand.getUserId())) {
            throw new IllegalStateException("# 포스트 신고글을 작성한 사용자가 일치하지 않아요...");
        }

        postReportRepository.delete(postReport);
        postReportEventPublisher.publishPostReportDeletedEvent(postReport);
    }
}
