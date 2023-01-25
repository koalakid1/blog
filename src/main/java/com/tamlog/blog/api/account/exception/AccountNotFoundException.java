package com.tamlog.blog.api.account.exception;

import com.tamlog.blog.advice.NotFoundException;

import static com.tamlog.blog.support.ExceptionUtil.errorMessageConcat;

public class AccountNotFoundException extends NotFoundException {

    private static final String MESSAGE = "계정이 존재하지 않습니다.";

    public AccountNotFoundException() {
        super(MESSAGE);
    }

    public AccountNotFoundException(String field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
