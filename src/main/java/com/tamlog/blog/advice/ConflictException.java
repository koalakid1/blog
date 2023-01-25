package com.tamlog.blog.advice;

import org.springframework.http.HttpStatus;

public class ConflictException extends BusinessException {
    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
