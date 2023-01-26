package com.tamlog.blog.advice;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
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