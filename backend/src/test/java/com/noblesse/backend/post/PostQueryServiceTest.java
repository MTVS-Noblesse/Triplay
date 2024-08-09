package com.noblesse.backend.post;

import com.noblesse.backend.post.dto.query.PostCoCommentDTO;
import com.noblesse.backend.post.dto.query.PostCommentDTO;
import com.noblesse.backend.post.dto.query.PostDTO;
import com.noblesse.backend.post.entity.Post;
import com.noblesse.backend.post.entity.PostCoComment;
import com.noblesse.backend.post.entity.PostComment;
import com.noblesse.backend.post.exception.PostCommentNotFoundException;
import com.noblesse.backend.post.exception.PostNotFoundException;
import com.noblesse.backend.post.repository.PostCoCommentRepository;
import com.noblesse.backend.post.repository.PostCommentRepository;
import com.noblesse.backend.post.repository.PostRepository;
import com.noblesse.backend.post.service.PostQueryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostQueryServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostCommentRepository postCommentRepository;

    @Mock
    private PostCoCommentRepository postCoCommentRepository;

    @InjectMocks
    private PostQueryService postQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * ### PostDTO Tests ###
     */
    @DisplayName(value = "# 포스트가 존재하면 postId에 해당하는 포스트를 조회하는 테스트")
    @Test
    @Order(1)
    void getPostByIdShouldReturnPostDTOWhenPostExists() {
        // Arrange
        Long postId = 1L;
        Post post = new Post("Test Title", "Test Content", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L);
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

    @DisplayName(value = "# 포스트가 존재하지 않으면 `PostNotFoundException`을 리턴받는 테스트")
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

    @DisplayName(value = "# post 테이블에 존재하는 모든 포스트를 페이지로 조회하는 테스트")
    @Test
    @Order(3)
    void getAllPostsShouldReturnPageOfPostDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Post> posts = Arrays.asList(
                new Post("Title 1", "Content 1", LocalDateTime.now(), LocalDateTime.now(), true, 1L, 1L, 1L),
                new Post("Title 2", "Content 2", LocalDateTime.now(), LocalDateTime.now(), true, 2L, 2L, 2L)
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

    @DisplayName(value = "# userId로 해당 사용자가 작성한 모든 포스트를 조회하는 테스트")
    @Test
    @Order(4)
    void getPostsByUserIdShouldReturnListOfPostDTOs() {
        // Arrange
        Long userId = 1L;
        List<Post> posts = Arrays.asList(
                new Post("Title 1", "Content 1", LocalDateTime.now(), LocalDateTime.now(), true, userId, 1L, 1L),
                new Post("Title 2", "Content 2", LocalDateTime.now(), LocalDateTime.now(), true, userId, 2L, 2L)
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
    @DisplayName(value = "# 포스트 댓글이 존재하면 postId에 해당하는 포스트 댓글을 조회하는 테스트")
    @Test
    @Order(5)
    void getPostCommentByIdShouldReturnPostCommentDTOWhenPostCommentExists() {
        // Arrange
        Long postCommentId = 1L;
        PostComment postComment = new PostComment("Test Content", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
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

    @DisplayName(value = "# 포스트 댓글이 존재하지 않으면 `PostCommentNotFoundException`을 리턴받는 테스트")
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

    @DisplayName(value = "# post_comment 테이블에 존재하는 모든 포스트 댓글을 페이지로 조회하는 테스트")
    @Test
    @Order(7)
    void getAllPostCommentsShouldReturnPageOfPostCommentDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<PostComment> postComments = Arrays.asList(
                new PostComment("Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostComment("Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
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

    @DisplayName(value = "# userId로 해당 사용자가 작성한 모든 포스트 댓글을 조회하는 테스트")
    @Test
    @Order(8)
    void getPostCommentsByUserIdShouldReturnListOfPostCommentDTOs() {
        // Arrange
        Long userId = 1L;
        List<PostComment> postComments = Arrays.asList(
                new PostComment("Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostComment("Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
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
    @DisplayName(value = "# 포스트 대댓글이 존재하면 postId에 해당하는 포스트 대댓글을 조회하는 테스트")
    @Test
    @Order(9)
    void getPostCoCommentByIdShouldReturnPostCoCommentDTOWhenPostCoCommentExists() {
        // Arrange
        Long postCoCommentId = 1L;
        PostCoComment postCoComment = new PostCoComment("Test Content", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L);
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

    @DisplayName(value = "# 포스트 대댓글이 존재하지 않으면 `PostCoCommentNotFoundException`을 리턴받는 테스트")
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

    @DisplayName(value = "# post_comment 테이블에 존재하는 모든 포스트 대댓글을 페이지로 조회하는 테스트")
    @Test
    @Order(11)
    void getAllPostCoCommentsShouldReturnPageOfPostCoCommentDTOs() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment("Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostCoComment("Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
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

    @DisplayName(value = "# userId로 해당 사용자가 작성한 모든 포스트 대댓글을 조회하는 테스트")
    @Test
    @Order(12)
    void getPostCoCommentsByUserIdShouldReturnListOfPostCoCommentDTOs() {
        // Arrange
        Long userId = 1L;
        List<PostCoComment> postCoComments = Arrays.asList(
                new PostCoComment("Test Content 1", LocalDateTime.now(), LocalDateTime.now(), 1L, 1L),
                new PostCoComment("Test Content 2", LocalDateTime.now(), LocalDateTime.now(), 2L, 2L)
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
}
