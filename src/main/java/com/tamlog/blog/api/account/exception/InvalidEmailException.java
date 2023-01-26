package com.tamlog.blog.api.account.exception;

import com.tamlog.blog.advice.BadRequestException;
import com.tamlog.blog.advice.ExceptionField;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class InvalidEmailException extends BadRequestException {

    private static final String MESSAGE = "이메일 형식이 올바르지 않습니다.";

    public InvalidEmailException() {
        super(MESSAGE);
    }

    public InvalidEmailException(ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(MESSAGE, exceptionField, value));
    }
}
