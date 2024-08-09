package com.noblesse.backend.post;

import com.noblesse.backend.post.dto.query.PostDTO;
import com.noblesse.backend.post.entity.Post;
import com.noblesse.backend.post.exception.PostNotFoundException;
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

    @InjectMocks
    private PostQueryService postQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName(value = "# Post가 존재하면 postId에 해당하는 Post를 조회하는 테스트")
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

    @DisplayName(value = "# Post가 존재하지 않으면 `PostNotFoundException`을 리턴받는 테스트")
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

    @DisplayName(value = "# post 테이블에 존재하는 모든 Post를 페이지로 조회하는 테스트")
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

    @DisplayName(value = "# userId로 해당 사용자가 작성한 모든 Post를 조회하는 테스트")
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
}
