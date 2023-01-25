package com.tamlog.blog.api.board.exception;

import com.tamlog.blog.advice.BadRequestException;

import static com.tamlog.blog.support.ExceptionUtil.errorMessageConcat;

public class InvalidContentException extends BadRequestException {

    private static final String MESSAGE = "본문은 1자 이상 5000자 이하여야 합니다.";

    public InvalidContentException() {
        super(MESSAGE);
    }

    public InvalidContentException(String field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
