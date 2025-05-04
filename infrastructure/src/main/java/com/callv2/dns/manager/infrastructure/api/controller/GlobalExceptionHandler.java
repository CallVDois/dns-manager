package com.callv2.dns.manager.infrastructure.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<ApiError> handle(final Throwable ex) {
        return ResponseEntity.internalServerError().body(ApiError.with("Internal Server Error " + ex.getMessage()));
    }

}
