package com.example.smsbe.exception;

import com.example.smsbe.response.ResponseWrapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseWrapper<?> handleUnwantedException(Exception e) {
        return new ResponseWrapper<>()
                .setStatusCode(500)
                .setMessage(e.getMessage());
    }

    @ExceptionHandler(AppException.class)
    public ResponseWrapper<?> handleAppException(AppException e) {
        return new ResponseWrapper<>()
                .setStatusCode(e.getStatusCode())
                .setMessage(e.getMessage());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseWrapper<?> handleNotFoundException(ChangeSetPersister.NotFoundException e) {
        return new ResponseWrapper<>()
                .setStatusCode(404)
                .setMessage(e.getMessage());
    }
}