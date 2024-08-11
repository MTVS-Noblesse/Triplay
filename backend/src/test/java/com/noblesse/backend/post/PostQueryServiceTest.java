package com.noblesse.backend.post;

import com.noblesse.backend.post.dto.query.PostCoCommentDTO;
import com.noblesse.backend.post.dto.query.PostCommentDTO;
import com.noblesse.backend.post.dto.query.PostDTO;
import com.noblesse.backend.post.dto.query.PostReportDTO;
import com.noblesse.backend.post.entity.Post;
import com.noblesse.backend.post.entity.PostCoComment;
import com.noblesse.backend.post.entity.PostComment;
import com.noblesse.backend.post.entity.PostReport;
import com.noblesse.backend.post.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.exception.PostNotFoundException;
import com.noblesse.backend.post.exception.PostReportNotFoundException;
import com.noblesse.backend.post.repository.PostCoCommentRepository;
import com.noblesse.backend.post.repository.PostCommentRepository;
import com.noblesse.backend.post.repository.PostReportRepository;
import com.noblesse.backend.post.repository.PostRepository;
import com.noblesse.backend.post.service.PostQueryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    @BeforeEach
    void setUp() {
        openMocks(this);
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
        assertThrows(PostNotFoundException.class, () -> postQueryService.getPostById(postId));
        verify(postRepository, times(1)).findById(postId);
    }

    @DisplayName(value = "#03. post 테이블에 존재하는 모든 포스트를 페이지로 조회하는 테스트")
    @Test
    @Order(3)
    void getAllPostsShouldReturnPageOfPostDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Post> posts = Arrays.asList(
                new Post(1L, "Title 1", "Content 1", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L),
                new Post(2L, "Title 2", "Content 2", LocalDateTime.now(), LocalDateTime.now(), true, 2L, 2L, 2L)
        );
        Page<Post> postPage = new PageImpl<>(posts, pageRequest, posts.size());
        when(postRepository.findAll(pageRequest))
                .thenReturn(postPage);

        // Act
        Page<PostDTO> result = postQueryService.getAllPosts(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Title 1", result.getContent().get(0).getPostTitle());
        assertEquals("Title 2", result.getContent().get(1).getPostTitle());
        verify(postRepository, times(1)).findAll(pageRequest);
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
        PostComment postComment = new PostComment(1L, "Test Content", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
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
        assertThrows(PostCommentNotFoundException.class, () -> postQueryService.getPostCommentById(postCommentId));
        verify(postCommentRepository, times(1)).findById(postCommentId);
    }

    @DisplayName(value = "#07. post_comment 테이블에 존재하는 모든 포스트 댓글을 페이지로 조회하는 테스트")
    @Test
    @Order(7)
    void getAllPostCommentsShouldReturnPageOfPostCommentDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<PostComment> postComments = Arrays.asList(
                new PostComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
        );
        Page<PostComment> postCommentPage = new PageImpl<>(postComments, pageRequest, postComments.size());
        when(postCommentRepository.findAll(pageRequest))
                .thenReturn(postCommentPage);

        // Act
        Page<PostCommentDTO> result = postQueryService.getAllPostComments(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Test Content 1", result.getContent().get(0).getPostCommentContent());
        assertEquals("Test Content 2", result.getContent().get(1).getPostCommentContent());
        verify(postCommentRepository, times(1)).findAll(pageRequest);
    }

    @DisplayName(value = "#08. userId로 해당 사용자가 작성한 모든 포스트 댓글을 조회하는 테스트")
    @Test
    @Order(8)
    void getPostCommentsByUserIdShouldReturnListOfPostCommentDTOs() {
        // Arrange
        Long userId = 1L;
        List<PostComment> postComments = Arrays.asList(
                new PostComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
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
        PostCoComment postCoComment = new PostCoComment(1L, "Test Content", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
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
        assertEquals("Test Content", result.getPostCommentContent());
        verify(postCoCommentRepository, times(1)).findById(postCoCommentId);
    }

    @DisplayName(value = "#10. 포스트 대댓글이 존재하지 않으면 `PostCoCommentNotFoundException`을 리턴받는 테스트")
    @Test
    @Order(10)
    void getPostCoCommentByIdShouldThrowExceptionWhenPostCoCommentDoesNotExist() {
        // Arrange
        Long postCommentId = 1L;
        when(postCommentRepository.findById(postCommentId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PostCommentNotFoundException.class, () -> postQueryService.getPostCommentById(postCommentId));
        verify(postCommentRepository, times(1)).findById(postCommentId);
    }

    @DisplayName(value = "#11. post_comment 테이블에 존재하는 모든 포스트 대댓글을 페이지로 조회하는 테스트")
    @Test
    @Order(11)
    void getAllPostCoCommentsShouldReturnPageOfPostCoCommentDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostCoComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
        );
        Page<PostCoComment> postCoCommentPage = new PageImpl<>(postCoComments, pageRequest, postCoComments.size());
        when(postCoCommentRepository.findAll(pageRequest))
                .thenReturn(postCoCommentPage);

        // Act
        Page<PostCoCommentDTO> result = postQueryService.getAllPostCoComments(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Test Content 1", result.getContent().get(0).getPostCommentContent());
        assertEquals("Test Content 2", result.getContent().get(1).getPostCommentContent());
        verify(postCoCommentRepository, times(1)).findAll(pageRequest);
    }

    @DisplayName(value = "#12. userId로 해당 사용자가 작성한 모든 포스트 대댓글을 조회하는 테스트")
    @Test
    @Order(12)
    void getPostCoCommentsByUserIdShouldReturnListOfPostCoCommentDTOs() {
        // Arrange
        Long userId = 1L;
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment(1L, "Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostCoComment(2L, "Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
        );
        when(postCoCommentRepository.findByUserId(userId))
                .thenReturn(postCoComments);

        // Act
        List<PostCoCommentDTO> result = postQueryService.getPostCoCommentsByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Test Content 1", result.get(0).getPostCommentContent());
        assertEquals("Test Content 2", result.get(1).getPostCommentContent());
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
        PostReport postReport = new PostReport("Test Content", 1L, 1L, 1L);
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
        assertThrows(PostReportNotFoundException.class, () -> postQueryService.getPostReportById(postReportId));
        verify(postReportRepository, times(1)).findById(postReportId);
    }

    @DisplayName(value = "#15. post_report 테이블에 존재하는 모든 포스트 신고를 페이지로 조회하는 테스트")
    @Test
    @Order(15)
    void getAllPostReportsShouldReturnPageOfPostReportDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<PostReport> postReports = Arrays.asList(
                new PostReport("Test Content 1", 1L, 1L, 1L),
                new PostReport("Test Content 2", 2L, 2L, 2L)
        );
        Page<PostReport> postReportPage = new PageImpl<>(postReports, pageRequest, postReports.size());
        when(postReportRepository.findAll(pageRequest))
                .thenReturn(postReportPage);

        // Act
        Page<PostReportDTO> result = postQueryService.getAllPostReports(pageRequest);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Test Content 1", result.getContent().get(0).getPostReportContent());
        assertEquals("Test Content 2", result.getContent().get(1).getPostReportContent());
        verify(postReportRepository, times(1)).findAll(pageRequest);
    }

    @DisplayName(value = "#16. userId로 해당 사용자의 모든 포스트 신고를 조회하는 테스트")
    @Test
    @Order(16)
    void getPostReportsByUserIdShouldReturnListOfPostReportDTOs() {
        // Arrange
        Long userId = 1L;
        List<PostReport> postReports = Arrays.asList(
                new PostReport("Test Content 1", 1L, 1L, 1L),
                new PostReport("Test Content 2", 2L, 2L, 2L)
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

    /**
     * ### Post 공통 Tests ###
     */
    @DisplayName("#17. 페이지네이션 경계값 테스트 - 첫 페이지")
    @Test
    @Order(17)
    void testFirstPagePagination() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Post> mockPage = new PageImpl<>(new ArrayList<>(), pageRequest, 0);
        when(postRepository.findAll(pageRequest))
                .thenReturn(mockPage);

        Page<PostDTO> result = postQueryService.getAllPosts(pageRequest);

        assertNotNull(result);
        assertTrue(result.isFirst());
        assertFalse(result.hasPrevious());
    }

    @DisplayName("#18. 최대 길이 제목을 가진 게시물 테스트")
    @Test
    @Order(18)
    void testPostWithMaxLengthTitle() {
        String maxLengthTitle = "a".repeat(255); // 가정: 제목 최대 길이가 255
        Post post = new Post(1L, maxLengthTitle, "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDTO result = postQueryService.getPostById(1L);

        assertEquals(maxLengthTitle, result.getPostTitle());
    }

    @DisplayName("#19. 여러 필드에 대한 정렬 테스트")
    @Test
    @Order(19)
    void testMultipleFieldSorting() {
        Sort sort = Sort.by(Sort.Order.desc("createdDateTime"), Sort.Order.asc("postTitle"));
        PageRequest pageRequest = PageRequest.of(0, 10, sort);

        Page<Post> mockPage = new PageImpl<>(Arrays.asList(
                new Post(1L, "B Post", "Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L),
                new Post(2L, "A Post", "Content", LocalDateTime.now().minusHours(1), LocalDateTime.now(), true, 1L, 1L, 1L)
        ));
        when(postRepository.findAll(pageRequest)).thenReturn(mockPage);

        Page<PostDTO> result = postQueryService.getAllPosts(pageRequest);

        assertEquals("B Post", result.getContent().get(0).getPostTitle());
        assertEquals("A Post", result.getContent().get(1).getPostTitle());
    }

    @DisplayName("#20. 잘못된 페이지 요청에 대한 예외 테스트")
    @Test
    @Order(20)
    void testInvalidPageRequest() {
        assertThrows(IllegalArgumentException.class, () -> {
            PageRequest pageRequest = PageRequest.of(-1, 10);
            postQueryService.getAllPosts(pageRequest);
        });
    }

    @DisplayName("#21. 복잡한 조건의 게시물 검색 테스트")
    @Test
    @Order(21)
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

    @DisplayName("#22. 대량 데이터 처리 성능 테스트")
    @Test
    @Order(22)
    void testLargeDataSetPerformance() {
        int largePageSize = 10000;
        PageRequest pageRequest = PageRequest.of(0, largePageSize);
        List<Post> largePosts = new ArrayList<>();
        for (int i = 0; i < largePageSize; i++) {
            largePosts.add(new Post((long) i, "Title " + i, "Content " + i, LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L));
        }
        Page<Post> postPage = new PageImpl<>(largePosts, pageRequest, largePosts.size());
        when(postRepository.findAll(pageRequest)).thenReturn(postPage);

        long startTime = System.currentTimeMillis();
        Page<PostDTO> result = postQueryService.getAllPosts(pageRequest);
        long endTime = System.currentTimeMillis();

        assertNotNull(result);
        assertEquals(largePageSize, result.getContent().size());
        assertTrue((endTime - startTime) < 2000, "처리 시간은 2초 미만이어야 합니다.");
    }

    @DisplayName("#23. 동시성 테스트")
    @Test
    @Order(23)
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

    @DisplayName("#24. PostDTO, PostCommentDTO, PostCoCommentDTO가 함께 조회되는지 확인하는 테스트")
    @Test
    @Order(24)
    void getPostWithCommentsAndCoCommentsShouldReturnPostWithRelatedCommentsAndCoComments() {
        // Arrange
        Long postId = 1L;
        Post post = new Post(postId, "Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);

        PostComment comment1 = new PostComment(1L, "Comment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, postId);
        PostComment comment2 = new PostComment(2L, "Comment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, postId);
        List<PostComment> comments = Arrays.asList(comment1, comment2);

        PostCoComment coComment1 = new PostCoComment(1L, "CoComment 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
        PostCoComment coComment2 = new PostCoComment(2L, "CoComment 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 1L);
        List<PostCoComment> coComments1 = Arrays.asList(coComment1, coComment2);

        PostCoComment coComment3 = new PostCoComment(3L, "CoComment 3", LocalDateTime.now(), LocalDateTime.now(), 3L, 2L);
        List<PostCoComment> coComments2 = List.of(coComment3);

        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));
        when(postCommentRepository.findByPostId(postId))
                .thenReturn(comments);
        when(postCoCommentRepository.findByPostCommentId(1L))
                .thenReturn(coComments1);
        when(postCoCommentRepository.findByPostCommentId(2L))
                .thenReturn(coComments2);

        // Act
        PostDTO result = postQueryService.getPostWithCommentsAndCoComments(postId);

        // Assert
        assertNotNull(result);
        assertEquals("Test Title", result.getPostTitle());
        assertEquals(2, result.getComments().size());

        PostCommentDTO firstComment = result.getComments().get(0);
        assertEquals("Comment 1", firstComment.getPostCommentContent());
        assertEquals(2, firstComment.getCoComments().size());

        PostCommentDTO secondComment = result.getComments().get(1);
        assertEquals("Comment 2", secondComment.getPostCommentContent());
        assertEquals(1, secondComment.getCoComments().size());

        assertEquals("CoComment 1", firstComment.getCoComments().get(0).getPostCommentContent());
        assertEquals("CoComment 2", firstComment.getCoComments().get(1).getPostCommentContent());
        assertEquals("CoComment 3", secondComment.getCoComments().get(0).getPostCommentContent());

        verify(postRepository, times(1)).findById(postId);
        verify(postCommentRepository, times(1)).findByPostId(postId);
        verify(postCoCommentRepository, times(1)).findByPostCommentId(1L);
        verify(postCoCommentRepository, times(1)).findByPostCommentId(2L);
    }
}
