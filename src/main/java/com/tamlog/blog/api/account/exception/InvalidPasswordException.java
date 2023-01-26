package com.tamlog.blog.api.account.exception;

import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.advice.UnauthorizedException;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class InvalidPasswordException extends UnauthorizedException {
    private static final String MESSAGE = "계정이 존재하지 않습니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }

    public InvalidPasswordException(ExceptionField field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
