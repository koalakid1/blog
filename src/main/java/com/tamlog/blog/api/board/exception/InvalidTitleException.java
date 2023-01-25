package com.tamlog.blog.api.board.exception;

import com.tamlog.blog.advice.BadRequestException;
import com.tamlog.blog.api.board.domain.Title;

import static com.tamlog.blog.support.ExceptionUtil.errorMessageConcat;

public class InvalidTitleException extends BadRequestException {
    private static final String MESSAGE = String.format("제목은 1자 이상 %d자 이하여야 합니다.", Title.MAX_LENGTH);

    public InvalidTitleException() {
        super(MESSAGE);
    }

    public InvalidTitleException(String field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
