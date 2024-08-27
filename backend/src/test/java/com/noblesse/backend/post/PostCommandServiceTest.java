package com.noblesse.backend.post;

import com.noblesse.backend.post.command.application.service.PostCommandService;
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
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class) // 단위 테스트에 보다 적합한 테스트
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostCommandServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostCommentRepository postCommentRepository;
    @Mock
    PostCoCommentRepository postCoCommentRepository;
    @Mock
    PostReportRepository postReportRepository;

    @Mock
    private PostDomainService postDomainService;

    @Mock
    private PostEventPublisher postEventPublisher;
    @Mock
    private PostCommentEventPublisher postCommentEventPublisher;
    @Mock
    private PostCoCommentEventPublisher postCoCommentEventPublisher;
    @Mock
    private PostReportEventPublisher postReportEventPublisher;

    private PostCommandService postCommandService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        postCommandService = new PostCommandService(
                postRepository,
                postCommentRepository,
                postCoCommentRepository,
                postReportRepository,
                postDomainService,
                postEventPublisher,
                postCommentEventPublisher,
                postCoCommentEventPublisher,
                postReportEventPublisher
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    /**
     * ### PostDTO Tests ###
     */
    @DisplayName("#01. 포스트 추가 테스트")
    @Test
    @Order(1)
    void createPostTest() {
        // Given
        PostDTO createCommand = new PostDTO("Test Title", "Test Content", true, 1L, 1L, 1L);
        Post savedPost = new Post(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        when(postRepository.save(any(Post.class)))
                .thenReturn(savedPost);

        // When
        Long postId = postCommandService.createPost(createCommand);

        // Then
        assertEquals(1L, postId);
        verify(postDomainService).validatePostContent(any(Post.class));
        verify(postEventPublisher).publishPostCreatedEvent(any(Post.class));
    }

    @DisplayName("#02. 포스트 수정 테스트")
    @Test
    @Order(2)
    void updatePostTest() {
        // Given
        Long postId = 1L;
        PostDTO updateCommand = new PostDTO(postId, "Updated Title", "Updated Content", true, 1L);
        Post existingPost = new Post(postId, "Old Title", "Old Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class)))
                .thenReturn(existingPost);

        // When
        postCommandService.updatePost(updateCommand);

        // Then
        verify(postDomainService).validatePostUpdate(existingPost, 1L);
        verify(postDomainService).validatePostContent(existingPost);
        verify(postEventPublisher).publishPostUpdatedEvent(existingPost);
    }

    @DisplayName("#03. 존재하지 않는 포스트 수정 시 예외 발생 테스트")
    @Test
    @Order(3)
    void updateNonExistentPostTest() {
        // Given
        Long nonExistentPostId = 999L;
        PostDTO updateCommand = new PostDTO(nonExistentPostId, "Updated Title", "Updated Content", true, 1L);

        when(postRepository.findById(nonExistentPostId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(PostNotFoundException.class,
                () -> postCommandService.updatePost(updateCommand));
    }

    @DisplayName("#04. 포스트 삭제 테스트")
    @Test
    @Order(4)
    void deletePostTest() {
        // Given
        Long postId = 1L;
        Long userId = 1L;
        PostDTO deleteCommand = new PostDTO(postId, userId);
        Post existingPost = new Post(postId, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), true, userId, 1L, 1L);

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(existingPost));
        when(postDomainService.canUserDeletePost(existingPost, userId))
                .thenReturn(false);

        // When
        postCommandService.deletePost(deleteCommand);

        // Then
        verify(postRepository).delete(existingPost);
        verify(postEventPublisher).publishPostDeletedEvent(existingPost);
    }

    @DisplayName("#05. 포스트 내용 유효성 검사 실패 테스트")
    @Test
    @Order(5)
    void createInvalidPostTest() {
        // Given
        PostDTO createCommand = new PostDTO("", "", true, 1L, 1L, 1L);

        doThrow(new IllegalArgumentException("Invalid post content"))
                .when(postDomainService)
                .validatePostContent(any(Post.class));

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> postCommandService.createPost(createCommand));
    }

    @DisplayName("#06. 권한 없는 사용자의 포스트 삭제 시도 테스트")
    @Test
    @Order(6)
    void deletePostWithoutPermissionTest() {
        // Given
        Long postId = 1L;
        Long userId = 2L; // 포스트 작성자와 다른 사용자
        PostDTO deleteCommand = new PostDTO(postId, userId);
        Post existingPost = new Post(postId, "Title", "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(existingPost));
        when(postDomainService.canUserDeletePost(existingPost, userId))
                .thenReturn(true);

        // When & Then
        assertThrows(IllegalStateException.class,
                () -> postCommandService.deletePost(deleteCommand));
    }

    /**
     * ### PostCommentDTO Tests ###
     */
    @DisplayName("#07. 새 포스트 댓글 생성 테스트")
    @Test
    @Order(7)
    void createPostCommentTest() {
        // Given
        PostCommentDTO createCommand = new PostCommentDTO("Test Comment", 1L, 1L);
        PostComment savedComment = new PostComment(1L, "Test Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCommentRepository.save(any(PostComment.class)))
                .thenReturn(savedComment);

        // When
        Long commentId = postCommandService.createPostComment(createCommand);

        // Then
        assertEquals(1L, commentId);
        verify(postDomainService).validatePostCommentContent(any(PostComment.class));
        verify(postCommentEventPublisher).publishPostCommentCreatedEvent(any(PostComment.class));
    }

    @DisplayName("#08. 포스트 댓글 수정 테스트")
    @Test
    @Order(8)
    void updatePostCommentTest() {
        // Given
        Long commentId = 1L;
        PostCommentDTO updateCommand = new PostCommentDTO(commentId, "Updated Comment", 1L);
        PostComment existingComment = new PostComment(commentId, "Old Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCommentRepository.findById(commentId))
                .thenReturn(Optional.of(existingComment));
        when(postCommentRepository.save(any(PostComment.class)))
                .thenReturn(existingComment);

        // When
        postCommandService.updatePostComment(updateCommand);

        // Then
        verify(postDomainService).validatePostCommentUpdate(existingComment, 1L);
        verify(postDomainService).validatePostCommentContent(existingComment);
        verify(postCommentEventPublisher).publishPostCommentUpdatedEvent(existingComment);
    }

    @DisplayName("#09. 존재하지 않는 포스트 댓글 수정 시 예외 발생 테스트")
    @Test
    @Order(9)
    void updateNonExistentPostCommentTest() {
        // Given
        Long nonExistentCommentId = 999L;
        PostCommentDTO updateCommand = new PostCommentDTO(nonExistentCommentId, "Updated Comment", 1L);

        when(postCommentRepository.findById(nonExistentCommentId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(PostCommentNotFoundException.class,
                () -> postCommandService.updatePostComment(updateCommand));
    }

    @DisplayName("#10. 포스트 댓글 삭제 테스트")
    @Test
    @Order(10)
    void deletePostCommentTest() {
        // Given
        Long commentId = 1L;
        Long userId = 1L;
        PostCommentDTO deleteCommand = new PostCommentDTO(commentId, userId);
        PostComment existingComment = new PostComment(commentId, "Comment", LocalDateTime.now(), LocalDateTime.now(), userId, 1L);

        when(postCommentRepository.findById(commentId))
                .thenReturn(Optional.of(existingComment));
        when(postDomainService.canUserDeletePostComment(existingComment, userId))
                .thenReturn(false);

        // When
        postCommandService.deletePostComment(deleteCommand);

        // Then
        verify(postCommentRepository).delete(existingComment);
        verify(postCommentEventPublisher).publishPostCommentDeletedEvent(existingComment);
    }

    @DisplayName("#11. 포스트 댓글 내용 유효성 검사 실패 테스트")
    @Test
    @Order(11)
    void createInvalidPostCommentTest() {
        // Given
        PostCommentDTO createCommand = new PostCommentDTO("", 1L, 1L);

        doThrow(new IllegalArgumentException("Invalid comment content"))
                .when(postDomainService)
                .validatePostCommentContent(any(PostComment.class));

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> postCommandService.createPostComment(createCommand));
    }

    @DisplayName("#12. 권한 없는 사용자의 포스트 댓글 삭제 시도 테스트")
    @Test
    @Order(12)
    void deletePostCommentWithoutPermissionTest() {
        // Given
        Long commentId = 1L;
        Long userId = 2L; // 댓글 작성자와 다른 사용자
        PostCommentDTO deleteCommand = new PostCommentDTO(commentId, userId);
        PostComment existingComment = new PostComment(commentId, "Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCommentRepository.findById(commentId))
                .thenReturn(Optional.of(existingComment));
        when(postDomainService.canUserDeletePostComment(existingComment, userId))
                .thenReturn(true);

        // When & Then
        assertThrows(IllegalStateException.class,
                () -> postCommandService.deletePostComment(deleteCommand));
    }

    /**
     * ### PostCoCommentDTO Tests ###
     */
    @DisplayName("#13. 포스트 대댓글 생성 테스트")
    @Test
    @Order(13)
    void createPostCoCommentTest() {
        // Given
        PostCoCommentDTO createCommand = new PostCoCommentDTO("Test Co-Comment", 1L, 1L);
        PostCoComment savedCoComment = new PostCoComment(1L, "Test Co-Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCoCommentRepository.save(any(PostCoComment.class)))
                .thenReturn(savedCoComment);

        // When
        Long coCommentId = postCommandService.createPostCoComment(createCommand);

        // Then
        assertEquals(1L, coCommentId);
        verify(postDomainService).validatePostCoCommentContent(any(PostCoComment.class));
        verify(postCoCommentEventPublisher).publishPostCoCommentCreatedEvent(any(PostCoComment.class));
    }

    @DisplayName("#14. 포스트 대댓글 수정 테스트")
    @Test
    @Order(14)
    void updatePostCoCommentTest() {
        // Given
        Long coCommentId = 1L;
        PostCoCommentDTO updateCommand = new PostCoCommentDTO(coCommentId, "Updated Co-Comment", 1L);
        PostCoComment existingCoComment = new PostCoComment(coCommentId, "Old Co-Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCoCommentRepository.findById(coCommentId))
                .thenReturn(Optional.of(existingCoComment));
        when(postCoCommentRepository.save(any(PostCoComment.class)))
                .thenReturn(existingCoComment);

        // When
        postCommandService.updatePostComment(updateCommand);

        // Then
        verify(postDomainService).validatePostCoCommentUpdate(existingCoComment, 1L);
        verify(postDomainService).validatePostCoCommentContent(existingCoComment);
        verify(postCoCommentEventPublisher).publishPostCoCommentUpdatedEvent(existingCoComment);
    }

    @DisplayName("#15. 존재하지 않는 포스트 대댓글 수정 시 예외 발생 테스트")
    @Test
    @Order(15)
    void updateNonExistentPostCoCommentTest() {
        // Given
        Long nonExistentCoCommentId = 999L;
        PostCoCommentDTO updateCommand = new PostCoCommentDTO(nonExistentCoCommentId, "Updated Co-Comment", 1L);

        when(postCoCommentRepository.findById(nonExistentCoCommentId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(PostCoCommentNotFoundException.class,
                () -> postCommandService.updatePostComment(updateCommand));
    }

    @DisplayName("#16. 포스트 대댓글 삭제 테스트")
    @Test
    @Order(16)
    void deletePostCoCommentTest() {
        // Given
        Long coCommentId = 1L;
        Long userId = 1L;
        PostCoCommentDTO deleteCommand = new PostCoCommentDTO(coCommentId, userId);
        PostCoComment existingCoComment = new PostCoComment(coCommentId, "Co-Comment", LocalDateTime.now(), LocalDateTime.now(), userId, 1L);

        when(postCoCommentRepository.findById(coCommentId))
                .thenReturn(Optional.of(existingCoComment));
        when(postDomainService.canUserDeletePostCoComment(existingCoComment, userId))
                .thenReturn(false);

        // When
        postCommandService.deletePostComment(deleteCommand);

        // Then
        verify(postCoCommentRepository).delete(existingCoComment);
        verify(postCoCommentEventPublisher).publishPostCoCommentDeletedEvent(existingCoComment);
    }

    @DisplayName("#17. 포스트 대댓글 내용 유효성 검사 실패 테스트")
    @Test
    @Order(17)
    void createInvalidPostCoCommentTest() {
        // Given
        PostCoCommentDTO createCommand = new PostCoCommentDTO("", 1L, 1L);

        doThrow(new IllegalArgumentException("Invalid co-comment content"))
                .when(postDomainService)
                .validatePostCoCommentContent(any(PostCoComment.class));

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> postCommandService.createPostCoComment(createCommand));
    }

    @DisplayName("#18. 권한 없는 사용자의 포스트 대댓글 삭제 시도 테스트")
    @Test
    @Order(18)
    void deletePostCoCommentWithoutPermissionTest() {
        // Given
        Long coCommentId = 1L;
        Long userId = 2L; // 대댓글 작성자와 다른 사용자
        PostCoCommentDTO deleteCommand = new PostCoCommentDTO(coCommentId, userId);
        PostCoComment existingCoComment = new PostCoComment(coCommentId, "Co-Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCoCommentRepository.findById(coCommentId))
                .thenReturn(Optional.of(existingCoComment));
        when(postDomainService.canUserDeletePostCoComment(existingCoComment, userId))
                .thenReturn(true);

        // When & Then
        assertThrows(IllegalStateException.class,
                () -> postCommandService.deletePostComment(deleteCommand));
    }

    /**
     * ### PostReportDTO Tests ###
     */
    @DisplayName("#19. 포스트 신고 생성 테스트")
    @Test
    @Order(19)
    void createPostReportTest() {
        // Given
        PostReportDTO createCommand = new PostReportDTO("Test Report", true, 1L, 1L, 1L);
        PostReport savedReport = new PostReport(1L, "Test Report", true, LocalDateTime.now(), null, 1L, 1L, 1L);

        when(postReportRepository.save(any(PostReport.class)))
                .thenReturn(savedReport);

        // When
        Long reportId = postCommandService.createPostReport(createCommand);

        // Then
        assertEquals(1L, reportId);
        verify(postDomainService).validatePostReportContent(any(PostReport.class));
        verify(postReportEventPublisher).publishPostReportCreatedEvent(any(PostReport.class));
    }

    @DisplayName("#20. 포스트 신고 수정 테스트")
    @Test
    @Order(20)
    void updatePostReportTest() {
        // Given
        Long reportId = 1L;
        PostReportDTO updateCommand = new PostReportDTO(reportId, "Updated Report", true, 1L);
        PostReport existingReport = new PostReport(reportId, "Old Report", false, LocalDateTime.now(), null, 1L, 1L, 1L);

        when(postReportRepository.findById(reportId))
                .thenReturn(Optional.of(existingReport));
        when(postReportRepository.save(any(PostReport.class)))
                .thenReturn(existingReport);

        // When
        postCommandService.updatePostReport(updateCommand);

        // Then
        verify(postDomainService).validatePostReportUpdate(existingReport, 1L);
        verify(postDomainService).validatePostReportContent(existingReport);
        verify(postReportEventPublisher).publishPostReportUpdatedEvent(existingReport);
    }

    @DisplayName("#21. 존재하지 않는 포스트 신고 수정 시 예외 발생 테스트")
    @Test
    @Order(21)
    void updateNonExistentPostReportTest() {
        // Given
        Long nonExistentReportId = 999L;
        PostReportDTO updateCommand = new PostReportDTO(nonExistentReportId, "Updated Report", true, 1L);

        when(postReportRepository.findById(nonExistentReportId))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(PostReportNotFoundException.class,
                () -> postCommandService.updatePostReport(updateCommand));
    }

    @DisplayName("#22. 포스트 신고 삭제 테스트")
    @Test
    @Order(22)
    void deletePostReportTest() {
        // Given
        Long reportId = 1L;
        Long userId = 1L;
        PostReportDTO deleteCommand = new PostReportDTO(reportId, userId);
        PostReport existingReport = new PostReport(reportId, "Report", true, LocalDateTime.now(), null, 1L, userId, 1L);

        when(postReportRepository.findById(reportId))
                .thenReturn(Optional.of(existingReport));
        when(postDomainService.canUserDeletePostReport(existingReport, userId))
                .thenReturn(false);

        // When
        postCommandService.deletePostComment(deleteCommand);

        // Then
        verify(postReportRepository).delete(existingReport);
        verify(postReportEventPublisher).publishPostReportDeletedEvent(existingReport);
    }

    @DisplayName("#23. 포스트 신고 내용 유효성 검사 실패 테스트")
    @Test
    @Order(23)
    void createInvalidPostReportTest() {
        // Given
        PostReportDTO createCommand = new PostReportDTO("", true, 1L, 1L, 1L);

        doThrow(new IllegalArgumentException("Invalid report content"))
                .when(postDomainService)
                .validatePostReportContent(any(PostReport.class));

        // When & Then
        assertThrows(IllegalArgumentException.class,
                () -> postCommandService.createPostReport(createCommand));
    }

    @DisplayName("#24. 권한 없는 사용자의 포스트 신고 삭제 시도 테스트")
    @Test
    @Order(24)
    void deletePostReportWithoutPermissionTest() {
        // Given
        Long reportId = 1L;
        Long userId = 2L; // 신고 작성자와 다른 사용자
        PostReportDTO deleteCommand = new PostReportDTO(reportId, userId);
        PostReport existingReport = new PostReport(reportId, "Report", true, LocalDateTime.now(), null, 1L, 1L, 1L);

        when(postReportRepository.findById(reportId))
                .thenReturn(Optional.of(existingReport));
        when(postDomainService.canUserDeletePostReport(existingReport, userId))
                .thenReturn(true);

        // When & Then
        assertThrows(IllegalStateException.class,
                () -> postCommandService.deletePostComment(deleteCommand));
    }
}
