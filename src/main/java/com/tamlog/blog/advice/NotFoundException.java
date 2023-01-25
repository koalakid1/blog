package com.tamlog.blog.advice;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException{

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
