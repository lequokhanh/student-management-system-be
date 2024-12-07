package com.example.smsbe.service.impl;

import com.example.smsbe.entity.Manager;
import com.example.smsbe.exception.AppException;
import com.example.smsbe.repository.ManagerRepository;
import com.example.smsbe.response.TokenResponse;
import com.example.smsbe.service.AuthService;
import com.example.smsbe.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ManagerRepository managerRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Manager loadUserByUsername(String username) {
        return managerRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(404, "User not found"));
    }

    public TokenResponse login(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        Manager manager = loadUserByUsername(username);
        return new TokenResponse()
                .setToken(jwtUtil.generateToken(manager))
                .setExpiresIn(jwtUtil.getExpirationTime());
    }

}
