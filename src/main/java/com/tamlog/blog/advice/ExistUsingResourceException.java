package com.tamlog.blog.advice;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class ExistUsingResourceException extends ConflictException {
    private static final String MESSAGE = "리소스와 연관된 데이터가 있어서 리소스를 삭제할 수 없습니다.";

    public ExistUsingResourceException() {
        super(MESSAGE);
    }

    public ExistUsingResourceException(ExceptionField exceptionField, Object value) {
        super(errorMessageConcat(MESSAGE, exceptionField, value));
    }
}
