package com.tamlog.blog.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentException(BindingResult bind) {
        String message = bind.getFieldErrors()
                .get(0)
                .getDefaultMessage();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ErrorResponse(status, message));
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BusinessException e) {
        HttpStatus status = e.getStatus();
        String message = e.getMessage();
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status, message));
    }
}