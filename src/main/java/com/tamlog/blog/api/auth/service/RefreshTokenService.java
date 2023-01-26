package com.tamlog.blog.api.auth.service;

import com.tamlog.blog.api.auth.domain.RefreshToken;
import com.tamlog.blog.api.auth.exception.InvalidRefreshTokenException;
import com.tamlog.blog.api.auth.repository.RefreshTokenRepository;
import com.tamlog.blog.support.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_REFRESH_TOKEN;
import static com.tamlog.blog.support.SecurityUtil.getCurrentAccountId;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void save(String token, LocalDateTime expiredAt) {
        delete();
        RefreshToken refreshToken = new RefreshToken(token, expiredAt, getCurrentAccountId());
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void delete() {
        refreshTokenRepository.deleteAllByAccountId(getCurrentAccountId());
    }

    @Transactional
    public void matches(String refreshToken) {
        RefreshToken savedRefreshToken = refreshTokenRepository.findByAccountId(getCurrentAccountId())
                .orElseThrow(InvalidRefreshTokenException::new);

        if (!tokenProvider.validateRefreshToken(refreshToken)) {
            refreshTokenRepository.delete(savedRefreshToken);
            throw new InvalidRefreshTokenException(EXCEPTION_REFRESH_TOKEN, refreshToken);
        }

        savedRefreshToken.validate(refreshToken);
    }
}
