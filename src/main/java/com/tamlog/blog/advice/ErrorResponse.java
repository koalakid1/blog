package com.tamlog.blog.advice;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

class ErrorResponse {
    LocalDateTime timeStamp = LocalDateTime.now();
    Integer status;
    String error;
    String message;


    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.error = status.name();
        this.message = message;
    }
}