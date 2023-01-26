package com.tamlog.blog.api.account.exception;

import com.tamlog.blog.advice.BadRequestException;

public class InvalidPasswordFormatException extends BadRequestException {

    private static final String MESSAGE = "비밀번호 형식이 올바르지 않습니다.";

    public InvalidPasswordFormatException() {
        super(MESSAGE);
    }
}
