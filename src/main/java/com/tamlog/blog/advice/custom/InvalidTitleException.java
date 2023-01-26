package com.tamlog.blog.advice.custom;

import com.tamlog.blog.advice.BadRequestException;
import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.api.board.domain.Title;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class InvalidTitleException extends BadRequestException {
    private static String MESSAGE = String.format("제목은 1자 이상 %d자 이하여야 합니다.", Title.MAX_LENGTH);

    public InvalidTitleException(Integer maxLength) {
        super(MESSAGE);
    }

    public InvalidTitleException(Integer maxLength, ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(MESSAGE, exceptionField, value));
    }
}
