package com.example.smsbe.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ResponseWrapper<T> {
    private int statusCode;
    private String message;
    private T data;
}
