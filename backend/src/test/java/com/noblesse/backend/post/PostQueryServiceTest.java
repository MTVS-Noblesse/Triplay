package com.noblesse.backend.post;

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
import com.noblesse.backend.post.query.application.service.PostQueryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostQueryServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostCommentRepository postCommentRepository;

    @Mock
    private PostCoCommentRepository postCoCommentRepository;

    @Mock
    private PostReportRepository postReportRepository;

    @InjectMocks
    private PostQueryService postQueryService;

    private AutoCloseable closeable;

    @BeforeEach
    void initMocks() {
        closeable = openMocks(this);
    }

    @AfterEach
    void closeMocks() throws Exception {
        closeable.close();
    }

    /**
     * ### PostDTO Tests ###
     */
    @DisplayName(value = "#01. 포스트가 존재하면 postId에 해당하는 포스트를 조회하는 테스트")
    @Test
    @Order(1)
    void getPostByIdShouldReturnPostDTOWhenPostExists() {
        // Arrange
        Long postId = 1L;
        Post post = new Post(postId, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        // Act
        PostDTO result = postQueryService.getPostById(postId);
//        System.out.println("result = " + result); // com.noblesse.backend.post.dto.query.PostDTO@27abb83e
//        System.out.println("result.getPostTitle() = " + result.getPostTitle()); // Test Title
//        System.out.println("result.getPostContent() = " + result.getPostContent()); // Test Content
//        System.out.println("result.getIsOpened() = " + result.getIsOpened()); // true
//        System.out.println("result.getCreatedDateTime() = " + result.getCreatedDateTime()); // 2024-08-09T15:24:13.747779900

        // Assert
        assertNotNull(result);
        assertEquals("Test Title", result.getPostTitle());
        assertEquals("Test Content", result.getPostContent());
        verify(postRepository, times(1)).findById(postId);
    }

    @DisplayName(value = "#02. 포스트가 존재하지 않으면 `PostNotFoundException`을 리턴받는 테스트")
    @Test
    @Order(2)
    void getPostByIdShouldThrowExceptionWhenPostDoesNotExist() {
        // Arrange
        Long postId = 1L;

        when(postRepository.findById(postId))
                .thenReturn(Optional.empty());

        // Act & Assert
        PostNotFoundException thrown = assertThrows(
                PostNotFoundException.class,
                () -> postQueryService.getPostById(postId)
        );
        assertEquals(String.format("포스트 ID %d 를 찾을 수 없어요...", postId), thrown.getMessage());
        verify(postRepository, times(1)).findById(postId);
    }

    @DisplayName(value = "#03. post 테이블에 존재하는 모든 포스트를 페이지로 조회하는 테스트")
    @Test
    @Order(3)
    void getAllPostsShouldReturnPageOfPostDTOs() {
        // Arrange
        List<Post> posts = Arrays.asList(
                new Post(1L, "Title 1", "Content 1", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L),
                new Post(2L, "Title 2", "Content 2", LocalDateTime.now(), LocalDateTime.now(), true, 2L, 2L, 2L)
        );

        when(postRepository.findAll())
                .thenReturn(posts);

        // Act
        List<PostDTO> result = postQueryService.getAllPosts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getPostTitle());
        assertEquals("Title 2", result.get(1).getPostTitle());
        verify(postRepository, times(1)).findAll();
    }

    @DisplayName(value = "#04. userId로 해당 사용자가 작성한 모든 포스트를 조회하는 테스트")
    @Test
    @Order(4)
    void getPostsByUserIdShouldReturnListOfPostDTOs() {
        // Arrange
        Long userId = 1L;
        List<Post> posts = Arrays.asList(
                new Post(1L, "Title 1", "Content 1", LocalDateTime.now(), LocalDateTime.now(), true, userId, 1L, 1L),
                new Post(2L, "Title 2", "Content 2", LocalDateTime.now(), LocalDateTime.now(), true, userId, 2L, 2L)
        );

        when(postRepository.findByUserId(userId))
                .thenReturn(posts);

        // Act
        List<PostDTO> result = postQueryService.getPostsByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getPostTitle());
        assertEquals("Title 2", result.get(1).getPostTitle());
        verify(postRepository, times(1)).findByUserId(userId);
    }

    /**
     * ### PostCommentDTO Tests ###
     */
    @DisplayName(value = "#05. 포스트 댓글이 존재하면 postCommentId에 해당하는 포스트 댓글을 조회하는 테스트")
    @Test
    @Order(5)
    void getPostCommentByIdShouldReturnPostCommentDTOWhenPostCommentExists() {
        // Arrange
        Long postCommentId = 1L;

        Post post = Post.builder().postId(1L).build();
        PostComment postComment = new PostComment(1L, "Test Content", LocalDateTime.now(), LocalDateTime.now(), 1L, post);

        when(postCommentRepository.findById(postCommentId))
                .thenReturn(Optional.of(postComment));

        // Act
        PostCommentDTO result = postQueryService.getPostCommentById(postCommentId);
//        System.out.println("result = " + result); // com.noblesse.backend.post.dto.query.PostCommentDTO@6ceb7b5e
//        System.out.println("result.getPostCommentContent() = " + result.getPostCommentContent()); // Test Content
//        System.out.println("result.getCreatedDateTime() = " + result.getCreatedDateTime()); // 2024-08-09T17:09:28.847988900
//        System.out.println("result.getUpdatedDateTime() = " + result.getUpdatedDateTime()); // 2024-08-09T17:09:28.847988900
//        System.out.println("result.getUserId() = " + result.getUserId()); // 1
//        System.out.println("result.getPostId() = " + result.getPostId()); // 1

        // Assert
        assertNotNull(result);
        assertEquals("Test Content", result.getPostCommentContent());
        verify(postCommentRepository, times(1)).findById(postCommentId);
    }

    @DisplayName(value = "#06. 포스트 댓글이 존재하지 않으면 `PostCommentNotFoundException`을 리턴받는 테스트")
    @Test
    @Order(6)
    void getPostCommentByIdShouldThrowExceptionWhenPostCommentDoesNotExist() {
        // Arrange
        Long postCommentId = 1L;

        when(postCommentRepository.findById(postCommentId))
                .thenReturn(Optional.empty());

        // Act & Assert
        PostCommentNotFoundException thrown = assertThrows(
                PostCommentNotFoundException.class,
                () -> postQueryService.getPostCommentById(postCommentId)
        );
        assertEquals(String.format("포스트 댓글 ID %d 를 찾을 수 없어요...", postCommentId), thrown.getMessage());
        verify(postCommentRepository, times(1)).findById(postCommentId);
    }

    @DisplayName(value = "#07. post_comment 테이블에 존재하는 모든 포스트 댓글을 페이지로 조회하는 테스트")
    @Test
    @Order(7)
    void getAllPostCommentsShouldReturnPageOfPostCommentDTOs() {
        // Arrange
        Post post = Post.builder().postId(1L).build();
        List<PostComment> postComments = Arrays.asList(
                new PostComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, post),
                new PostComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, post)
        );

        when(postCommentRepository.findAll())
                .thenReturn(postComments);

        // Act
        List<PostCommentDTO> result = postQueryService.getAllPostComments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Content 1", result.get(0).getPostCommentContent());
        assertEquals("Test Content 2", result.get(1).getPostCommentContent());
        verify(postCommentRepository, times(1)).findAll();
    }

    @DisplayName(value = "#08. userId로 해당 사용자가 작성한 모든 포스트 댓글을 조회하는 테스트")
    @Test
    @Order(8)
    void getPostCommentsByUserIdShouldReturnListOfPostCommentDTOs() {
        // Arrange
        Long userId = 1L;

        Post post = Post.builder().postId(1L).build();
        List<PostComment> postComments = Arrays.asList(
                new PostComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, post),
                new PostComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, post)
        );

        when(postCommentRepository.findByUserId(userId))
                .thenReturn(postComments);

        // Act
        List<PostCommentDTO> result = postQueryService.getPostCommentsByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Content 1", result.get(0).getPostCommentContent());
        assertEquals("Test Content 2", result.get(1).getPostCommentContent());
        verify(postCommentRepository, times(1)).findByUserId(userId);
    }

    /**
     * ### PostCoCommentDTO Tests ###
     */
    @DisplayName(value = "#09. 포스트 대댓글이 존재하면 postCoCommentId에 해당하는 포스트 대댓글을 조회하는 테스트")
    @Test
    @Order(9)
    void getPostCoCommentByIdShouldReturnPostCoCommentDTOWhenPostCoCommentExists() {
        // Arrange
        Long postCoCommentId = 1L;

        Post post = Post.builder().postId(1L).build();
        PostComment postComment = new PostComment(1L, "Parent Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, post);
        PostCoComment postCoComment = new PostCoComment(1L, "Test Co Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, postComment);

        when(postCoCommentRepository.findById(postCoCommentId))
                .thenReturn(Optional.of(postCoComment));

        // Act
        PostCoCommentDTO result = postQueryService.getPostCoCommentById(postCoCommentId);
//        System.out.println("result = " + result); // com.noblesse.backend.post.dto.query.PostCoCommentDTO@6ceb7b5e
//        System.out.println("result.getPostCommentContent() = " + result.getPostCommentContent()); // Test Content
//        System.out.println("result.getCreatedDateTime() = " + result.getCreatedDateTime()); // 2024-08-09T16:54:44.008140300
//        System.out.println("result.getUpdatedDateTime() = " + result.getUpdatedDateTime()); // 2024-08-09T16:54:44.008140300
//        System.out.println("result.getUserId() = " + result.getUserId()); // 1
//        System.out.println("result.getPostId() = " + result.getPostCommentId()); // 1

        // Assert
        assertNotNull(result);
        assertEquals("Test Co Comment", result.getPostCoCommentContent());
        verify(postCoCommentRepository, times(1)).findById(postCoCommentId);
    }

    @DisplayName(value = "#10. 포스트 대댓글이 존재하지 않으면 `PostCoCommentNotFoundException`을 리턴받는 테스트")
    @Test
    @Order(10)
    void getPostCoCommentByIdShouldThrowExceptionWhenPostCoCommentDoesNotExist() {
        // Arrange
        Long postCoCommentId = 1L;

        when(postCoCommentRepository.findById(postCoCommentId))
                .thenReturn(Optional.empty());

        // Act & Assert
        PostCoCommentNotFoundException thrown = assertThrows(
                PostCoCommentNotFoundException.class,
                () -> postQueryService.getPostCoCommentById(postCoCommentId));
        assertEquals(String.format("포스트 대댓글 ID %d 를 찾을 수 없어요...", postCoCommentId), thrown.getMessage());
        verify(postCoCommentRepository, times(1)).findById(postCoCommentId);
    }

    @DisplayName(value = "#11. post_co_comment 테이블에 존재하는 모든 포스트 대댓글을 페이지로 조회하는 테스트")
    @Test
    @Order(11)
    void getAllPostCoCommentsShouldReturnPageOfPostCoCommentDTOs() {
        // Arrange
        Post post = Post.builder().postId(1L).build();
        PostComment postComment = new PostComment(1L, "Parent Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, post);
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment(1L, "Test Co Comment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, postComment),
                new PostCoComment(2L, "Test Co Comment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, postComment)
        );

        when(postCoCommentRepository.findAll())
                .thenReturn(postCoComments);

        // Act
        List<PostCoCommentDTO> result = postQueryService.getAllPostCoComments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Co Comment 1", result.get(0).getPostCoCommentContent());
        assertEquals("Test Co Comment 2", result.get(1).getPostCoCommentContent());
        verify(postCoCommentRepository, times(1)).findAll();
    }

    @DisplayName(value = "#12. userId로 해당 사용자가 작성한 모든 포스트 대댓글을 조회하는 테스트")
    @Test
    @Order(12)
    void getPostCoCommentsByUserIdShouldReturnListOfPostCoCommentDTOs() {
        // Arrange
        Long userId = 1L;

        Post post = Post.builder().postId(1L).build();
        PostComment postComment = new PostComment(1L, "Parent Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, post);
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment(1L, "Test Co Comment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, postComment),
                new PostCoComment(2L, "Test Co Comment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, postComment)
        );

        when(postCoCommentRepository.findByUserId(userId))
                .thenReturn(postCoComments);

        // Act                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           org.springframework.http.ResponseEntity
        List<PostCoCommentDTO> result = postQueryService.getPostCoCommentsByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Co Comment 1", result.get(0).getPostCoCommentContent());
        assertEquals("Test Co Comment 2", result.get(1).getPostCoCommentContent());
        verify(postCoCommentRepository, times(1)).findByUserId(userId);
    }

    /**
     * ### PostReportDTO Tests ###
     */
    @DisplayName(value = "#13. 포스트 신고가 존재하면 postReportId에 해당하는 포스트 신고를 조회하는 테스트")
    @Test
    @Order(13)
    void getPostReportByIdShouldReturnPostReportDTOWhenPostReportExists() {
        // Arrange
        Long postReportId = 1L;

        Post post = Post.builder().postId(1L).build();
        PostReport postReport = new PostReport(postReportId, "Test Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, post);

        when(postReportRepository.findById(postReportId))
                .thenReturn(Optional.of(postReport));

        // Act
        PostReportDTO result = postQueryService.getPostReportById(postReportId);
//        System.out.println("result = " + result); // result = com.noblesse.backend.post.dto.query.PostReportDTO@3f92c349
//        System.out.println("result.getPostReportContent() = " + result.getPostReportContent()); // Test Content
//        System.out.println("result.getUserId() = " + result.getUserId()); // 1
//        System.out.println("result.getPostId() = " + result.getPostId()); // 1

        // Assert
        assertNotNull(result);
        assertEquals("Test Content", result.getPostReportContent());
        verify(postReportRepository, times(1)).findById(postReportId);
    }

    @DisplayName(value = "#14. 포스트 신고가 존재하지 않으면 `PostReportNotFoundException`을 리턴받는 테스트")
    @Test
    @Order(14)
    void getPostReportByIdShouldThrowExceptionWhenPostReportDoesNotExist() {
        // Arrange
        Long postReportId = 1L;
        when(postReportRepository.findById(postReportId))
                .thenReturn(Optional.empty());

        // Act & Assert
        PostReportNotFoundException thrown = assertThrows(
                PostReportNotFoundException.class,
                () -> postQueryService.getPostReportById(postReportId)
        );
        assertEquals(String.format("포스트 신고 ID %d 를 찾을 수 없어요...", postReportId), thrown.getMessage());
        verify(postReportRepository, times(1)).findById(postReportId);
    }

    @DisplayName(value = "#15. post_report 테이블에 존재하는 모든 포스트 신고를 페이지로 조회하는 테스트")
    @Test
    @Order(15)
    void getAllPostReportsShouldReturnPageOfPostReportDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        Post post = Post.builder().postId(1L).build();
        List<PostReport> postReports = Arrays.asList(
                new PostReport(1L, "어그로 포스트 1", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, post),
                new PostReport(2L, "어그로 포스트 2", LocalDateTime.now(), LocalDateTime.now(), true, 2L, 2L, post)
        );
        Page<PostReport> postReportPage = new PageImpl<>(postReports, pageRequest, postReports.size());

        when(postReportRepository.findAll(pageRequest))
                .thenReturn(postReportPage);

        // Act
        Page<PostReportDTO> result = postQueryService.getAllPostReports(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("어그로 포스트 1", result.getContent().get(0).getPostReportContent());
        assertEquals("어그로 포스트 2", result.getContent().get(1).getPostReportContent());
        verify(postReportRepository, times(1)).findAll(pageRequest);
    }

    @DisplayName(value = "#16. userId로 해당 사용자의 모든 포스트 신고를 조회하는 테스트")
    @Test
    @Order(16)
    void getPostReportsByUserIdShouldReturnListOfPostReportDTOs() {
        // Arrange
        Long userId = 1L;

        Post post = Post.builder().postId(1L).build();
        List<PostReport> postReports = Arrays.asList(
                new PostReport(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, post),
                new PostReport(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), true, 2L, 2L, post)
        );

        when(postReportRepository.findByUserId(userId))
                .thenReturn(postReports);

        // Act
        List<PostReportDTO> result = postQueryService.getPostReportsByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Content 1", result.get(0).getPostReportContent());
        assertEquals("Test Content 2", result.get(1).getPostReportContent());
        verify(postReportRepository, times(1)).findByUserId(userId);
    }

    @DisplayName("#17. 특정 포스트에 대한 모든 신고를 조회하는 테스트")
    @Test
    @Order(17)
    void getReportsForPostTest() {
        // Arrange
        Post post = Post.builder().postId(1L).build();
        List<PostReport> postReports = Arrays.asList(
                new PostReport(1L, "어그로 포스트 1", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, post),
                new PostReport(2L, "어그로 포스트 2", LocalDateTime.now(), LocalDateTime.now(), true, 2L, 2L, post)
        );

        when(postReportRepository.findByPostId(post.getPostId()))
                .thenReturn(postReports);

        // Act
        List<PostReportDTO> result = postQueryService.getPostReportsByPostId(post.getPostId());

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("어그로 포스트 1", result.get(0).getPostReportContent());
        assertEquals("어그로 포스트 2", result.get(1).getPostReportContent());
        verify(postReportRepository, times(1)).findByPostId(post.getPostId());
    }

    /**
     * ### Post 공통 Tests ###
     */
    @DisplayName("#18. 전체 게시물 목록 조회 테스트")
    @Test
    @Order(18)
    void testGetAllPosts() {
        List<Post> mockPosts = Arrays.asList(
                new Post(1L, "Title 1", "Content 1", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L),
                new Post(2L, "Title 2", "Content 2", LocalDateTime.now(), LocalDateTime.now(), true, 2L, 2L, 2L)
        );

        when(postRepository.findAll()).thenReturn(mockPosts);

        List<PostDTO> result = postQueryService.getAllPosts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Title 1", result.get(0).getPostTitle());
        assertEquals("Title 2", result.get(1).getPostTitle());
    }

    @DisplayName("#19. 최대 길이 제목을 가진 게시물 테스트")
    @Test
    @Order(19)
    void testPostWithMaxLengthTitle() {
        String maxLengthTitle = "a".repeat(255); // 가정: 제목 최대 길이가 255
        Post post = new Post(1L, maxLengthTitle, "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDTO result = postQueryService.getPostById(1L);

        assertEquals(maxLengthTitle, result.getPostTitle());
    }

    @DisplayName("#20. 게시물 목록 정렬 테스트")
    @Test
    @Order(20)
    void testSortedPosts() {
        List<Post> mockPosts = Arrays.asList(
                new Post(1L, "B Post", "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L),
                new Post(2L, "A Post", "Content", LocalDateTime.now().minusHours(1), LocalDateTime.now(), true, 1L, 1L, 1L)
        );

        when(postRepository.findAll()).thenReturn(mockPosts);

        List<PostDTO> result = postQueryService.getAllPosts();

        assertEquals(2, result.size());
        assertEquals("B Post", result.get(0).getPostTitle());
        assertEquals("A Post", result.get(1).getPostTitle());
    }

    @DisplayName("#21. 빈 게시물 목록 조회 테스트")
    @Test
    @Order(21)
    void testEmptyPostList() {
        when(postRepository.findAll()).thenReturn(new ArrayList<>());

        List<PostDTO> result = postQueryService.getAllPosts();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @DisplayName("#22. 복잡한 조건의 게시물 검색 테스트")
    @Test
    @Order(22)
    void testComplexPostSearch() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();
        List<Long> userIds = Arrays.asList(1L, 2L, 3L);

        List<Post> mockPosts = Arrays.asList(
                new Post(1L, "Post 1", "Content", startDate.plusDays(1), startDate.plusDays(1), true, 1L, 1L, 1L),
                new Post(2L, "Post 2", "Content", startDate.plusDays(2), startDate.plusDays(2), true, 2L, 1L, 1L)
        );

        when(postRepository.findByCreatedDateTimeBetweenAndUserIdInAndIsOpenedTrue(startDate, endDate, userIds))
                .thenReturn(mockPosts);

        List<PostDTO> result = postQueryService.searchPosts(startDate, endDate, userIds, true);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(post -> post.getCreatedDateTime().isAfter(startDate) && post.getCreatedDateTime().isBefore(endDate)));
        assertTrue(result.stream().allMatch(post -> userIds.contains(post.getUserId())));
        assertTrue(result.stream().allMatch(PostDTO::getIsOpened));
    }

    @DisplayName("#23. 대량 데이터 처리 성능 테스트")
    @Test
    @Order(23)
    void testLargeDataSetPerformance() {
        int largeSize = 10000;
        List<Post> largePosts = new ArrayList<>();
        for (int i = 0; i < largeSize; i++) {
            largePosts.add(new Post((long) i, "Title " + i, "Content " + i, LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L));
        }

        when(postRepository.findAll()).thenReturn(largePosts);

        long startTime = System.currentTimeMillis();
        List<PostDTO> result = postQueryService.getAllPosts();
        long endTime = System.currentTimeMillis();

        assertNotNull(result);
        assertEquals(largeSize, result.size());
        assertTrue((endTime - startTime) < 2000, "처리 시간은 2초 미만이어야 합니다.");
    }

    @DisplayName("#24. 동시성 테스트")
    @Test
    @Order(24)
    void testConcurrentAccess() throws InterruptedException {
        // 동시에 실행할 스레드의 수를 정의함
        int threadCount = 10;

        // 모든 스레드가 작업을 완료할 때까지 대기하기 위한 CountDownLatch 클래스를 생성함
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 성공적으로 포스트를 조회한 횟수를 안전하게 카운트하기 위한 AtomicInteger 클래스를 생성함
        AtomicInteger successCount = new AtomicInteger(0);

        // 테스트용 mock Post 객체를 생성함
        Post mockPost = new Post(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        // postRepository의 findById 메서드가 호출될 때 앞서 생성한 mockPost를 반환하도록 설정함
        when(postRepository.findById(1L))
                .thenReturn(Optional.of(mockPost));

        // threadCount 만큼의 스레드를 생성하고 실행함
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    // 각 스레드에서 postQueryService의 getPostById 메서드를 호출함
                    PostDTO result = postQueryService.getPostById(1L);

                    // 결과가 null이 아니고 제목이 "Test Title"인 경우 성공 카운트를 증가시킴
                    if (result != null && result.getPostId() == 1L) {
                        successCount.incrementAndGet();
                    }
                } finally {
                    // 작업 완료 후 CountDownLatch 카운트를 감소시킴
                    latch.countDown();
                }
            }).start();
        }

        // 최대 5초 동안 모든 스레드가 작업을 완료할 때까지 대기함
        assertTrue(latch.await(5, TimeUnit.SECONDS));

        // 모든 스레드가 성공적으로 포스트를 조회했는지 확인함
        assertEquals(threadCount, successCount.get());
    }

    @DisplayName("#25. 포스트, 포스트 댓글, 포스트 대댓글이 함께 조회되는지 확인하는 테스트")
    @Test
    @Order(25)
    void getPostWithCommentsAndCoCommentsShouldReturnPostWithRelatedCommentsAndCoComments() {
        // Arrange
        Long postId = 1L;
        Post post = Post.builder().postId(postId).postTitle("Test Title").build();

        PostComment comment1 = new PostComment(1L, "Test Comment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, post);
        PostComment comment2 = new PostComment(2L, "Test Comment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, post);
        List<PostComment> comments = Arrays.asList(comment1, comment2);

        PostCoComment coComment1 = new PostCoComment(1L, "Test Co Comment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, comment1);
        PostCoComment coComment2 = new PostCoComment(2L, "Test Co Comment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, comment1);
        List<PostCoComment> coComments1 = Arrays.asList(coComment1, coComment2);

        PostCoComment coComment3 = new PostCoComment(3L, "Test Co Comment 3", LocalDateTime.now(), LocalDateTime.now(), 3L, comment2);
        List<PostCoComment> coComments2 = List.of(coComment3);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postCommentRepository.findByPostId(postId)).thenReturn(comments);
        when(postCoCommentRepository.findByPostCommentId(1L)).thenReturn(coComments1);
        when(postCoCommentRepository.findByPostCommentId(2L)).thenReturn(coComments2);

        // Act
        PostDTO result = postQueryService.getPostWithCommentsAndCoComments(postId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Title", result.getPostTitle());
        assertEquals(2, result.getComments().size());

        PostCommentDTO firstComment = result.getComments().get(0);
        assertEquals("Test Comment 1", firstComment.getPostCommentContent());
        assertEquals(2, firstComment.getCoComments().size());

        PostCommentDTO secondComment = result.getComments().get(1);
        assertEquals("Test Comment 2", secondComment.getPostCommentContent());
        assertEquals(1, secondComment.getCoComments().size());

        assertEquals("Test Co Comment 1", firstComment.getCoComments().get(0).getPostCoCommentContent());
        assertEquals("Test Co Comment 2", firstComment.getCoComments().get(1).getPostCoCommentContent());
        assertEquals("Test Co Comment 3", secondComment.getCoComments().get(0).getPostCoCommentContent());

        verify(postRepository, times(1)).findById(postId);
        verify(postCommentRepository, times(1)).findByPostId(postId);
        verify(postCoCommentRepository, times(1)).findByPostCommentId(1L);
        verify(postCoCommentRepository, times(1)).findByPostCommentId(2L);
    }
}
