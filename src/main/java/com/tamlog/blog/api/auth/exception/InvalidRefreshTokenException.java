package com.tamlog.blog.api.auth.exception;

import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.advice.UnauthorizedException;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class InvalidRefreshTokenException extends UnauthorizedException {

    private static final String MESSAGE = "리프레시 토큰이 유효하지 않습니다.";

    public InvalidRefreshTokenException() {
        super(MESSAGE);
    }

    public InvalidRefreshTokenException(ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(MESSAGE, exceptionField, value));
    }
}
