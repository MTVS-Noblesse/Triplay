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

    @DisplayName(value = "#03. post 테이블에 존재하는 모든 포스트를 조회하는 테스트")
    @Test
    @Order(3)
    void getAllPostsShouldReturnListOfPostDTOs() {
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
        PostComment postComment = new PostComment(postCommentId, "Test Content", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCommentRepository.findById(postCommentId))
                .thenReturn(Optional.of(postComment));

        // Act
        PostCommentDTO result = postQueryService.getPostCommentById(postCommentId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Content", result.getPostCommentContent());
        assertEquals(1L, result.getPostId());
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

    @DisplayName(value = "#07. post_comment 테이블에 존재하는 모든 포스트 댓글을 조회하는 테스트")
    @Test
    @Order(7)
    void getAllPostCommentsShouldReturnListOfPostCommentDTOs() {
        // Arrange
        List<PostComment> postComments = Arrays.asList(
                new PostComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 1L)
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

    @DisplayName(value = "#08. postId로 해당 포스트의 모든 댓글을 조회하는 테스트")
    @Test
    @Order(8)
    void getPostCommentsByPostIdShouldReturnListOfPostCommentDTOs() {
        // Arrange
        Long postId = 1L;
        List<PostComment> postComments = Arrays.asList(
                new PostComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, postId),
                new PostComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, postId)
        );

        when(postCommentRepository.findByPostId(postId))
                .thenReturn(postComments);

        // Act
        List<PostCommentDTO> result = postQueryService.getPostCommentsByPostId(postId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Content 1", result.get(0).getPostCommentContent());
        assertEquals("Test Content 2", result.get(1).getPostCommentContent());
        assertEquals(postId, result.get(0).getPostId());
        assertEquals(postId, result.get(1).getPostId());
        verify(postCommentRepository, times(1)).findByPostId(postId);
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
        PostCoComment postCoComment = new PostCoComment(postCoCommentId, "Test Co Comment", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCoCommentRepository.findById(postCoCommentId))
                .thenReturn(Optional.of(postCoComment));

        // Act
        PostCoCommentDTO result = postQueryService.getPostCoCommentById(postCoCommentId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Co Comment", result.getPostCoCommentContent());
        assertEquals(1L, result.getPostCommentId());
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

    @DisplayName(value = "#11. post_co_comment 테이블에 존재하는 모든 포스트 대댓글을 조회하는 테스트")
    @Test
    @Order(11)
    void getAllPostCoCommentsShouldReturnListOfPostCoCommentDTOs() {
        // Arrange
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment(1L, "Test Co Comment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostCoComment(2L, "Test Co Comment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 1L)
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

    @DisplayName(value = "#12. postCommentId로 해당 댓글의 모든 대댓글을 조회하는 테스트")
    @Test
    @Order(12)
    void getPostCoCommentsByPostCommentIdShouldReturnListOfPostCoCommentDTOs() {
        // Arrange
        Long postCommentId = 1L;
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment(1L, "Test Co Comment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, postCommentId),
                new PostCoComment(2L, "Test Co Comment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, postCommentId)
        );

        when(postCoCommentRepository.findByPostCommentId(postCommentId))
                .thenReturn(postCoComments);

        // Act
        List<PostCoCommentDTO> result = postQueryService.getPostCoCommentsByPostCommentId(postCommentId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Co Comment 1", result.get(0).getPostCoCommentContent());
        assertEquals("Test Co Comment 2", result.get(1).getPostCoCommentContent());
        assertEquals(postCommentId, result.get(0).getPostCommentId());
        assertEquals(postCommentId, result.get(1).getPostCommentId());
        verify(postCoCommentRepository, times(1)).findByPostCommentId(postCommentId);
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
        PostReport postReport = new PostReport(postReportId, "Test Content", true, LocalDateTime.now(), LocalDateTime.now(), 1L, 1L, 1L);

        when(postReportRepository.findById(postReportId))
                .thenReturn(Optional.of(postReport));

        // Act
        PostReportDTO result = postQueryService.getPostReportById(postReportId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Content", result.getPostReportContent());
        assertEquals(1L, result.getPostId());
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

    @DisplayName(value = "#15. post_report 테이블에 존재하는 모든 포스트 신고를 조회하는 테스트")
    @Test
    @Order(15)
    void getAllPostReportsShouldReturnListOfPostReportDTOs() {
        // Arrange
        List<PostReport> postReports = Arrays.asList(
                new PostReport(1L, "어그로 포스트 1", true, LocalDateTime.now(), LocalDateTime.now(), 1L, 1L, 1L),
                new PostReport(2L, "어그로 포스트 2", true, LocalDateTime.now(), LocalDateTime.now(), 2L, 2L, 2L)
        );

        when(postReportRepository.findAll())
                .thenReturn(postReports);

        // Act
        List<PostReportDTO> result = postQueryService.getAllPostReports();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("어그로 포스트 1", result.get(0).getPostReportContent());
        assertEquals("어그로 포스트 2", result.get(1).getPostReportContent());
        verify(postReportRepository, times(1)).findAll();
    }

    @DisplayName(value = "#16. postId로 해당 포스트의 모든 신고를 조회하는 테스트")
    @Test
    @Order(16)
    void getPostReportsByPostIdShouldReturnListOfPostReportDTOs() {
        // Arrange
        Long postId = 1L;
        List<PostReport> postReports = Arrays.asList(
                new PostReport(1L, "Test Content 1", true, LocalDateTime.now(), LocalDateTime.now(), 1L, 1L, postId),
                new PostReport(2L, "Test Content 2", true, LocalDateTime.now(), LocalDateTime.now(), 2L, 2L, postId)
        );

        when(postReportRepository.findByPostId(postId))
                .thenReturn(postReports);

        // Act
        List<PostReportDTO> result = postQueryService.getPostReportsByPostId(postId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Content 1", result.get(0).getPostReportContent());
        assertEquals("Test Content 2", result.get(1).getPostReportContent());
        assertEquals(postId, result.get(0).getPostId());
        assertEquals(postId, result.get(1).getPostId());
        verify(postReportRepository, times(1)).findByPostId(postId);
    }

    @DisplayName("#17. 특정 포스트에 대한 모든 신고를 조회하는 테스트")
    @Test
    @Order(17)
    void getReportsForPostTest() {
        // Arrange
        Long postId = 1L;
        List<PostReport> postReports = Arrays.asList(
                new PostReport(1L, "어그로 포스트 1", true, LocalDateTime.now(), LocalDateTime.now(), 1L, 1L, postId),
                new PostReport(2L, "어그로 포스트 2", true, LocalDateTime.now(), LocalDateTime.now(), 2L, 2L, postId)
        );

        when(postReportRepository.findByPostId(postId))
                .thenReturn(postReports);

        // Act
        List<PostReportDTO> result = postQueryService.getPostReportsByPostId(postId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("어그로 포스트 1", result.get(0).getPostReportContent());
        assertEquals("어그로 포스트 2", result.get(1).getPostReportContent());
        verify(postReportRepository, times(1)).findByPostId(postId);
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

        when(postRepository.findByWrittenDatetimeBetweenAndUserIdInAndIsOpened(startDate, endDate, userIds, true))
                .thenReturn(mockPosts);

        List<PostDTO> result = postQueryService.searchPosts(startDate, endDate, userIds, true);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(post -> post.getWrittenDatetime().isAfter(startDate) && post.getWrittenDatetime().isBefore(endDate)));
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
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        Post mockPost = new Post(1L, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(mockPost));

        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                try {
                    PostDTO result = postQueryService.getPostById(1L);
                    if (result != null && result.getPostId() == 1L) {
                        successCount.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(threadCount, successCount.get());
    }

    @DisplayName("#26. 최대 길이 내용을 가진 포스트 댓글 테스트")
    @Test
    @Order(26)
    void testPostCommentWithMaxLengthContent() {
        String maxLengthContent = "a".repeat(1000); // 가정: 댓글 최대 길이가 1000
        PostComment postComment = new PostComment(1L, maxLengthContent, LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCommentRepository.findById(1L)).thenReturn(Optional.of(postComment));

        PostCommentDTO result = postQueryService.getPostCommentById(1L);

        assertEquals(maxLengthContent, result.getPostCommentContent());
    }

    @DisplayName("#27. 빈 포스트 댓글 목록 조회 테스트")
    @Test
    @Order(27)
    void testEmptyPostCommentList() {
        when(postCommentRepository.findAll()).thenReturn(new ArrayList<>());

        List<PostCommentDTO> result = postQueryService.getAllPostComments();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @DisplayName("#28. 최대 길이 내용을 가진 포스트 대댓글 테스트")
    @Test
    @Order(28)
    void testPostCoCommentWithMaxLengthContent() {
        String maxLengthContent = "a".repeat(500); // 가정: 대댓글 최대 길이가 500
        PostCoComment postCoComment = new PostCoComment(1L, maxLengthContent, LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);

        when(postCoCommentRepository.findById(1L)).thenReturn(Optional.of(postCoComment));

        PostCoCommentDTO result = postQueryService.getPostCoCommentById(1L);

        assertEquals(maxLengthContent, result.getPostCoCommentContent());
    }

    @DisplayName("#29. 빈 포스트 대댓글 목록 조회 테스트")
    @Test
    @Order(29)
    void testEmptyPostCoCommentList() {
        when(postCoCommentRepository.findAll()).thenReturn(new ArrayList<>());

        List<PostCoCommentDTO> result = postQueryService.getAllPostCoComments();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @DisplayName("#30. 최대 길이 내용을 가진 포스트 신고 테스트")
    @Test
    @Order(30)
    void testPostReportWithMaxLengthContent() {
        String maxLengthContent = "a".repeat(1000); // 가정: 신고 내용 최대 길이가 1000
        PostReport postReport = new PostReport(1L, maxLengthContent, true, LocalDateTime.now(), LocalDateTime.now(), 1L, 1L, 1L);

        when(postReportRepository.findById(1L)).thenReturn(Optional.of(postReport));

        PostReportDTO result = postQueryService.getPostReportById(1L);

        assertEquals(maxLengthContent, result.getPostReportContent());
    }

    @DisplayName("#31. 빈 포스트 신고 목록 조회 테스트")
    @Test
    @Order(31)
    void testEmptyPostReportList() {
        when(postReportRepository.findAll()).thenReturn(new ArrayList<>());

        List<PostReportDTO> result = postQueryService.getAllPostReports();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @DisplayName("#32. 처리되지 않은 포스트 신고 조회 테스트")
    @Test
    @Order(32)
    void testUnprocessedPostReports() {
        List<PostReport> unprocessedReports = Arrays.asList(
                new PostReport(1L, "Unprocessed Report 1", false, LocalDateTime.now(), null, 1L, 1L, 1L),
                new PostReport(2L, "Unprocessed Report 2", false, LocalDateTime.now(), null, 2L, 2L, 2L)
        );

        when(postReportRepository.findByIsReportedFalse()).thenReturn(unprocessedReports);

        List<PostReportDTO> result = postQueryService.getUnprocessedPostReports();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().noneMatch(PostReportDTO::getIsReported));
        assertNull(result.get(0).getProcessedDatetime());
        assertNull(result.get(1).getProcessedDatetime());
    }
}
