package com.tamlog.blog.api.board.exception;

import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.advice.NotFoundException;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class BoardNotFoundException extends NotFoundException {
    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";

    public BoardNotFoundException() {
        super(MESSAGE);
    }

    public BoardNotFoundException(ExceptionField field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
