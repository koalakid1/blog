package com.tamlog.blog.api.account.exception;

import com.tamlog.blog.advice.BadRequestException;
import com.tamlog.blog.advice.ExceptionField;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class InvalidNicknameException extends BadRequestException {

    private static final String MESSAGE = "닉네임 형식이 올바르지 않습니다.";

    public InvalidNicknameException(ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(MESSAGE, exceptionField, value));
    }
}
