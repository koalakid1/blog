package com.tamlog.blog.api.auth.exception;

import com.tamlog.blog.advice.ConflictException;
import com.tamlog.blog.advice.ExceptionField;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class AlreadyExistAccountException extends ConflictException {
    private static final String MESSAGE = "이미 가입한 회원입니다.";

    public AlreadyExistAccountException() {
        super(MESSAGE);
    }

    public AlreadyExistAccountException(ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(MESSAGE, exceptionField, value));
    }
}
