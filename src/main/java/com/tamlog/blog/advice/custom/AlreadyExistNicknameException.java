package com.tamlog.blog.advice.custom;

import com.tamlog.blog.advice.ConflictException;
import com.tamlog.blog.advice.ExceptionField;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class AlreadyExistNicknameException extends ConflictException {
    private static final String MESSAGE = "이미 존재하는 닉네임입니다.";

    public AlreadyExistNicknameException() {
        super(MESSAGE);
    }

    public AlreadyExistNicknameException(ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(MESSAGE, exceptionField, value));
    }
}
