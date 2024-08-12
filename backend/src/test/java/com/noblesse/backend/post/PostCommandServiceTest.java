package com.noblesse.backend.post;

import com.noblesse.backend.post.command.application.dto.CreatePostCommandDTO;
import com.noblesse.backend.post.command.application.dto.DeletePostCommandDTO;
import com.noblesse.backend.post.command.application.dto.UpdatePostCommandDTO;
import com.noblesse.backend.post.command.application.service.PostCommandService;
import com.noblesse.backend.post.command.domain.publisher.PostEventPublisher;
import com.noblesse.backend.post.command.domain.service.PostDomainService;
import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.common.exception.PostNotFoundException;
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
    private PostRepository postRepository;

    @Mock
    private PostDomainService postDomainService;

    @Mock
    private PostEventPublisher postEventPublisher;

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
        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // Act
        Long result = postCommandService.createPost(createCommand);

        // Assert
        assertEquals(1L, result);
        verify(postDomainService, times(1)).validatePostContent(any(Post.class));
        verify(postRepository, times(1)).save(any(Post.class));
        verify(postEventPublisher, times(1)).publishPostCreatedEvent(any(Post.class));
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
        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(existingPost);

        // Act
        assertDoesNotThrow(() -> postCommandService.updatePost(updateCommand));

        // Assert
        verify(postDomainService, times(1)).validatePostUpdate(any(Post.class), eq(1L));
        verify(postDomainService, times(1)).validatePostContent(any(Post.class));
        verify(postRepository, times(1)).save(any(Post.class));
        verify(postEventPublisher, times(1)).publishPostUpdatedEvent(any(Post.class));
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

        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PostNotFoundException.class, () -> postCommandService.updatePost(updateCommand));
    }

    @DisplayName("#04. 포스트 삭제 테스트")
    @Test
    @Order(4)
    void deletePostTest() {
        // Arrange
        DeletePostCommandDTO deleteCommand = new DeletePostCommandDTO(1L, 1L);
        Post existingPost = new Post(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));
        when(postDomainService.canUserDeletePost(any(Post.class), eq(1L))).thenReturn(false);

        // Act
        assertDoesNotThrow(() -> postCommandService.deletePost(deleteCommand));

        // Assert
        verify(postRepository, times(1)).delete(existingPost);
        verify(postEventPublisher, times(1)).publishPostDeletedEvent(existingPost);
    }

    @DisplayName("#05. 권한 없는 사용자의 포스트 삭제 시도 테스트")
    @Test
    @Order(5)
    void deletePostWithoutPermissionTest() {
        // Arrange
        DeletePostCommandDTO deleteCommand = new DeletePostCommandDTO(1L, 2L);
        Post existingPost = new Post(1L, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(existingPost));
        when(postDomainService.canUserDeletePost(any(Post.class), eq(2L))).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> postCommandService.deletePost(deleteCommand));
    }

    @DisplayName("#06. 존재하지 않는 포스트 삭제 시도 테스트")
    @Test
    @Order(6)
    void deleteNonExistingPostTest() {
        // Arrange
        DeletePostCommandDTO deleteCommand = new DeletePostCommandDTO(999L, 1L);
        when(postRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PostNotFoundException.class, () -> postCommandService.deletePost(deleteCommand));
    }
}
