package com.tamlog.blog.advice.custom;

import com.tamlog.blog.advice.BadRequestException;
import com.tamlog.blog.advice.ExceptionField;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class InvalidContentException extends BadRequestException {

    private static String MESSAGE = "본문은 1자 이상 %d자 이하여야 합니다.";

    public InvalidContentException(Integer maxLength) {
        super(String.format(MESSAGE, maxLength));
    }

    public InvalidContentException(Integer maxLength, ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(String.format(MESSAGE, maxLength), exceptionField, value));
    }
}
