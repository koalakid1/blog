package com.tamlog.blog.advice;

import com.tamlog.blog.api.board.domain.Content;

import static com.tamlog.blog.support.ExceptionUtil.errorMessageConcat;

public class InvalidContentException extends BadRequestException {

    private static final String MESSAGE = String.format("본문은 1자 이상 %d자 이하여야 합니다.", Content.MAX_LENGTH);

    public InvalidContentException() {
        super(MESSAGE);
    }

    public InvalidContentException(String field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
