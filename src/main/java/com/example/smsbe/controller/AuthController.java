package com.example.smsbe.controller;

import com.example.smsbe.response.ResponseWrapper;
import com.example.smsbe.response.TokenResponse;
import com.example.smsbe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseWrapper<TokenResponse> login(String username, String password) {
        return new ResponseWrapper<TokenResponse>()
                .setStatusCode(200)
                .setMessage("Login successful")
                .setData(authService.login(username, password));
    }
}
