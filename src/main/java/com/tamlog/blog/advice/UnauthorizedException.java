package com.tamlog.blog.advice;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ForbiddenException {
    private static final String MESSAGE = "접근 권한이 없습니다.";

    public UnauthorizedException() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
