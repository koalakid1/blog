package com.tamlog.blog.advice;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ForbiddenException {
    private static final String MESSAGE = "접근 권한이 없습니다.";

    private static final HttpStatus status = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException() {
        super(MESSAGE, status);
    }

    public UnauthorizedException(String message) {
        super(message, status);
    }
}
