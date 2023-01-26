package com.tamlog.blog.api.auth.repository;

import com.tamlog.blog.api.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByAccountId(Long accountId);

    void deleteAllByAccountId(Long accountId);
}
