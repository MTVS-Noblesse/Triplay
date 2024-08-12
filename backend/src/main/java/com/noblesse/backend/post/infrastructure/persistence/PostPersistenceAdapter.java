package com.noblesse.backend.post.infrastructure.persistence;

import com.noblesse.backend.post.common.entity.Post;
import com.noblesse.backend.post.query.infrastructure.persistence.repository.PostRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostPersistenceAdapter {

    /**
     * - 애플리케이션 계층과 영속성 계층 사이의 인터페이스 역할
     * - 데이터베이스 조작에 대한 세부 사항을 추상화
     * - 애플리케이션 로직이 특정 데이터 접근 기술에 의존하지 않도롬 함
     */
    private final PostRepository postRepository;

    public PostPersistenceAdapter(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
