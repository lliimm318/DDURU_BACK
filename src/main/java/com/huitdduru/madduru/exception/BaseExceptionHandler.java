package com.huitdduru.madduru.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> baseExceptionHandle(final Exception e) {
        return new ResponseEntity<>(new ErrorResponse(400, e.getMessage()),
                HttpStatus.valueOf(400));
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> baseExceptionHandle(final BaseException e) {
        final ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponse(errorCode.getStatus(), errorCode.getMessage()),
                HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> exceptionHandle(final Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse(500, "server error"),
                HttpStatus.valueOf(500));
    }

}
