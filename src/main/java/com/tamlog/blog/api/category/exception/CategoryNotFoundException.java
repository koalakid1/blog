package com.tamlog.blog.api.category.exception;

import com.tamlog.blog.advice.NotFoundException;

import static com.tamlog.blog.support.ExceptionUtil.errorMessageConcat;

public class CategoryNotFoundException extends NotFoundException {
    private static final String MESSAGE = "카테고리를 찾을 수 없습니다.";

    public CategoryNotFoundException(String field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
