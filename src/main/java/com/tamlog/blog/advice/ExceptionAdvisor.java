package com.tamlog.blog.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.errorCode.getHttpStatus().value())
                .body(new ErrorResponse(e.errorCode));
    }

    class ErrorResponse {
        LocalDateTime timeStamp = LocalDateTime.now();
        Integer status;
        String error;
        String code;
        String message;

        public ErrorResponse(ErrorCode errorCode) {
            this.status = errorCode.getHttpStatus().value();
            this.error = errorCode.getHttpStatus().name();
            this.code = errorCode.name();
            this.message = errorCode.getDetail();
        }

    }
}