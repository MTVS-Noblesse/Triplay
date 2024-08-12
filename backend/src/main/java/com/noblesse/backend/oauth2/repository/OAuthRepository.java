package com.noblesse.backend.oauth2.repository;

import com.noblesse.backend.oauth2.entity.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OAuthRepository extends JpaRepository<OAuthUser, Long> {
    Optional<OAuthUser> findByUserName(String userName);
}
