package com.example.smsbe.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppException extends RuntimeException {

    private int statusCode;

    public AppException(int statusCode,String message) {
        super(message);
        this.statusCode = statusCode;
    }
}