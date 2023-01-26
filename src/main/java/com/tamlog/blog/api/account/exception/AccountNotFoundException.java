package com.tamlog.blog.api.account.exception;

import com.tamlog.blog.advice.ExceptionField;
import com.tamlog.blog.advice.NotFoundException;

import static com.tamlog.blog.advice.ExceptionField.errorMessageConcat;

public class AccountNotFoundException extends NotFoundException {

    private static final String MESSAGE = "계정이 존재하지 않습니다.";

    public AccountNotFoundException() {
        super(MESSAGE);
    }

    public AccountNotFoundException(ExceptionField field, Object value) {
        super(errorMessageConcat(MESSAGE, field, value));
    }
}
