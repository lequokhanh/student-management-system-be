package com.example.smsbe.service;

import com.example.smsbe.entity.Manager;
import com.example.smsbe.response.TokenResponse;

public interface AuthService {
    Manager loadUserByUsername(String username);
    TokenResponse login(String username, String password);
}
