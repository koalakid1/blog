package com.tamlog.blog.api.board.exception;

import com.tamlog.blog.advice.NotFoundException;

import static com.tamlog.blog.support.ExceptionUtil.errorMessageConcat;

public class BoardNotFoundException extends NotFoundException {
    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";

    public BoardNotFoundException() {
        super(MESSAGE);
    }

    public BoardNotFoundException(String field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
