package com.example.smsbe.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TokenResponse {
    private String token;
    private int expiresIn;
}
