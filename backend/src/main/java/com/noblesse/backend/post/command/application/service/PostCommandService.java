package com.noblesse.backend.post.command.application.service;

import com.noblesse.backend.post.command.application.dto.CreatePostCommandDTO;
import com.noblesse.backend.post.command.application.dto.DeletePostCommandDTO;
import com.noblesse.backend.post.command.application.dto.UpdatePostCommandDTO;
import com.noblesse.backend.post.command.domain.publisher.PostEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.common.exception.PostNotFoundException;
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
     * 3. Command Handler의 역할을 대신함.
     */
    private final PostRepository postRepository;
    private final PostDomainService postDomainService;
    private final PostEventPublisher postEventPublisher;

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
}
