package com.noblesse.backend.post;

import com.noblesse.backend.post.command.application.dto.*;
import com.noblesse.backend.post.command.application.service.PostCommandService;
import com.noblesse.backend.post.command.domain.publisher.PostCommentEventPublisher;
import com.noblesse.backend.post.command.domain.publisher.PostEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.common.entity.PostComment;
import com.noblesse.backend.post.common.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.common.exception.PostNotFoundException;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostCommentRepository;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class) // 단위 테스트에 보다 적합한 테스트
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostCommandServiceTest {

    @Mock
    private PostEventPublisher postEventPublisher;

    @Mock
    private PostCommentEventPublisher postCommentEventPublisher;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostCommentRepository postCommentRepository;

    @Mock
    private PostDomainService postDomainService;

    @InjectMocks
    private PostCommandService postCommandService;

    @DisplayName("#01. 새 포스트 생성 테스트")
    @Test
    @Order(1)
    void createPostTest() {
        // Arrange
        CreatePostCommandDTO createCommand = CreatePostCommandDTO.builder()
                .postTitle("Test Title")
                .postContent("Test Content")
                .isOpened(true)
                .userId(1L)
                .tripId(1L)
                .clipId(1L)
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .build();

        Post savedPost = Post.builder()
                .postId(1L)
                .postTitle("Test Title")
                .postContent("Test Content")
                .isOpened(true)
                .userId(1L)
                .tripId(1L)
                .clipId(1L)
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .build();

        // postRepository.save() 메서드가 savedPost를 반환하도록 설정
        when(postRepository.save(any(Post.class)))
                .thenReturn(savedPost);

        // Act
        Long result = postCommandService.createPost(createCommand);

        // Assert
        assertEquals(1L, result);
        verify(postDomainService, times(1))
                .validatePostContent(any(Post.class));
        verify(postRepository, times(1))
                .save(any(Post.class));
        verify(postEventPublisher, times(1))
                .publishPostCreatedEvent(any(Post.class));
    }

    @DisplayName("#02. 포스트 수정 테스트")
    @Test
    @Order(2)
    void updatePostTest() {
        // Arrange
        UpdatePostCommandDTO updateCommand = UpdatePostCommandDTO.builder()
                .postId(1L)
                .postTitle("Updated Title")
                .postContent("Updated Content")
                .isOpened(true)
                .userId(1L)
                .build();

        Post existingPost = new Post(1L, "Old Title", "Old Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class)))
                .thenReturn(existingPost);

        // Act
        assertDoesNotThrow(
                () -> postCommandService.updatePost(updateCommand)
        );

        // Assert
        verify(postDomainService, times(1))
                .validatePostUpdate(any(Post.class), eq(1L));
        verify(postDomainService, times(1))
                .validatePostContent(any(Post.class));
        verify(postRepository, times(1))
                .save(any(Post.class));
        verify(postEventPublisher, times(1))
                .publishPostUpdatedEvent(any(Post.class));
    }

    @DisplayName("#03. 존재하지 않는 포스트 수정 시 예외 발생 테스트")
    @Test
    @Order(3)
    void updateNonExistingPostTest() {
        // Arrange
        UpdatePostCommandDTO updateCommand = UpdatePostCommandDTO.builder()
                .postId(999L)
                .postTitle("Updated Title")
                .postContent("Updated Content")
                .isOpened(true)
                .userId(1L)
                .build();

        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                PostNotFoundException.class,
                () -> postCommandService.updatePost(updateCommand)
        );
    }

    @DisplayName("#04. 포스트 삭제 테스트")
    @Test
    @Order(4)
    void deletePostTest() {
        // Arrange
        DeletePostCommandDTO deleteCommand = new DeletePostCommandDTO(1L, 1L);
        Post existingPost = new Post(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(existingPost));
        when(postDomainService.canUserDeletePost(any(Post.class), eq(1L)))
                .thenReturn(false);

        // Act
        assertDoesNotThrow(
                () -> postCommandService.deletePost(deleteCommand)
        );

        // Assert
        verify(postRepository, times(1))
                .delete(existingPost);
        verify(postEventPublisher, times(1))
                .publishPostDeletedEvent(existingPost);
    }

    @DisplayName("#05. 권한 없는 사용자의 포스트 삭제 시도 테스트")
    @Test
    @Order(5)
    void deletePostWithoutPermissionTest() {
        // Arrange
        DeletePostCommandDTO deleteCommand = new DeletePostCommandDTO(1L, 2L);
        Post existingPost = new Post(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(existingPost));
        when(postDomainService.canUserDeletePost(any(Post.class), eq(2L)))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                IllegalStateException.class,
                () -> postCommandService.deletePost(deleteCommand)
        );
    }

    @DisplayName("#06. 존재하지 않는 포스트 삭제 시도 테스트")
    @Test
    @Order(6)
    void deleteNonExistingPostTest() {
        // Arrange
        DeletePostCommandDTO deleteCommand = new DeletePostCommandDTO(999L, 1L);
        when(postRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                PostNotFoundException.class,
                () -> postCommandService.deletePost(deleteCommand)
        );
    }

    @DisplayName("#07. 새 포스트 댓글 생성 테스트")
    @Test
    @Order(7)
    void createPostCommentTest() {
        // Arrange
        CreatePostCommentCommandDTO createCommand = CreatePostCommentCommandDTO.builder()
                .postCommentContent("Test Comment")
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .userId(1L)
                .postId(1L)
                .build();

        PostComment savedPostComment = PostComment.builder()
                .postCommentContent("Test Comment")
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .userId(1L)
                .postId(1L)
                .build();

        // postCommentRepository.save() 메서드가 savedPost를 반환하도록 설정
        when(postCommentRepository.save(any(PostComment.class)))
                .thenReturn(savedPostComment);

        // Act
        Long result = postCommandService.createPostComment(createCommand);

        // Assert
        assertEquals(1L, result);
        verify(postDomainService, times(1))
                .validatePostCommentContent(any(PostComment.class));
        verify(postCommentRepository, times(1))
                .save(any(PostComment.class));
        verify(postCommentEventPublisher, times(1))
                .publishPostCommentCreatedEvent(any(PostComment.class));
    }

    @DisplayName("#08. 포스트 댓글 수정 테스트")
    @Test
    @Order(8)
    void updatePostCommentTest() {
        // Arrange
        UpdatePostCommentCommandDTO updateCommand = UpdatePostCommentCommandDTO.builder()
                .postCommentId(1L)
                .postCommentContent("Updated Comment")
                .userId(1L)
                .build();

        PostComment existingPostComment = new PostComment(1L, "Old Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        when(postCommentRepository.findById(1L))
                .thenReturn(Optional.of(existingPostComment));
        when(postCommentRepository.save(any(PostComment.class)))
                .thenReturn(existingPostComment);

        // Act
        assertDoesNotThrow(
                () -> postCommandService.updatePostComment(updateCommand)
        );

        // Assert
        verify(postDomainService, times(1))
                .validatePostCommentUpdate(any(PostComment.class), eq(1L));
        verify(postDomainService, times(1))
                .validatePostCommentContent(any(PostComment.class));
        verify(postCommentRepository, times(1))
                .save(any(PostComment.class));
        verify(postCommentEventPublisher, times(1))
                .publishPostCommentUpdatedEvent(any(PostComment.class));
    }

    @DisplayName("#09. 존재하지 않는 포스트 댓글 수정 시 예외 발생 테스트")
    @Test
    @Order(9)
    void updateNonExistingPostCommentTest() {
        // Arrange
        UpdatePostCommentCommandDTO updateCommand = UpdatePostCommentCommandDTO.builder()
                .postCommentId(999L)
                .postCommentContent("Updated Comment")
                .userId(1L)
                .build();

        when(postCommentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                PostCommentNotFoundException.class,
                () -> postCommandService.updatePostComment(updateCommand)
        );
    }

    @DisplayName("#10. 포스트 댓글 삭제 테스트")
    @Test
    @Order(10)
    void deletePostCommentTest() {
        // Arrange
        DeletePostCommentCommandDTO deleteCommand = new DeletePostCommentCommandDTO(1L, 1L);
        PostComment existingPostComment = new PostComment(1L, "Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        when(postCommentRepository.findById(1L))
                .thenReturn(Optional.of(existingPostComment));
        when(postDomainService.canUserDeletePostComment(any(PostComment.class), eq(1L)))
                .thenReturn(false);

        // Act
        assertDoesNotThrow(
                () -> postCommandService.deletePostComment(deleteCommand)
        );

        // Assert
        verify(postCommentRepository, times(1))
                .delete(existingPostComment);
        verify(postCommentEventPublisher, times(1))
                .publishPostCommentDeletedEvent(existingPostComment);
    }

    @DisplayName("#11. 권한 없는 사용자의 포스트 댓글 삭제 시도 테스트")
    @Test
    @Order(11)
    void deletePostCommentWithoutPermissionTest() {
        // Arrange
        DeletePostCommentCommandDTO deleteCommand = new DeletePostCommentCommandDTO(1L, 2L);
        PostComment existingPostComment = new PostComment(1L, "Content", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        when(postCommentRepository.findById(1L))
                .thenReturn(Optional.of(existingPostComment));
        when(postDomainService.canUserDeletePostComment(any(PostComment.class), eq(2L)))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                IllegalStateException.class,
                () -> postCommandService.deletePostComment(deleteCommand)
        );
    }

    @DisplayName("#12. 존재하지 않는 포스트 댓글 삭제 시도 테스트")
    @Test
    @Order(12)
    void deleteNonExistingPostCommentTest() {
        // Arrange
        DeletePostCommentCommandDTO deleteCommand = new DeletePostCommentCommandDTO(999L, 1L);
        when(postCommentRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                PostCommentNotFoundException.class,
                () -> postCommandService.deletePostComment(deleteCommand)
        );
    }
}
