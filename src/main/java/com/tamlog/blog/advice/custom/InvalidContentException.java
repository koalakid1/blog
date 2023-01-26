package com.tamlog.blog.advice.custom;

import com.tamlog.blog.advice.BadRequestException;
import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.api.board.domain.Content;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class InvalidContentException extends BadRequestException {

    private static final String MESSAGE = String.format("본문은 1자 이상 %d자 이하여야 합니다.", Content.MAX_LENGTH);

    public InvalidContentException() {
        super(MESSAGE);
    }

    public InvalidContentException(ExceptionField field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
