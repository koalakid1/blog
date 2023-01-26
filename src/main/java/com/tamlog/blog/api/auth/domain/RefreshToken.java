package com.tamlog.blog.api.auth.domain;

import com.tamlog.blog.api.auth.exception.InvalidRefreshTokenException;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_EXPIRES_AT;
import static com.tamlog.blog.advice.ExceptionField.EXCEPTION_REFRESH_TOKEN;

@Getter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", nullable = false)
    private Long id;

    @Column(name = "refresh_token", nullable = false)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    protected RefreshToken() {
    }

    public RefreshToken(String token, LocalDateTime expiresAt, Long accountId) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.accountId = accountId;
    }

    public void validate(String token) {
        if (LocalDateTime.now().isAfter(expiresAt)) {
            throw new InvalidRefreshTokenException(EXCEPTION_EXPIRES_AT,expiresAt);
        }

        if (!this.token.equals(token)) {
            throw new InvalidRefreshTokenException(EXCEPTION_REFRESH_TOKEN, token);
        }
    }
}
