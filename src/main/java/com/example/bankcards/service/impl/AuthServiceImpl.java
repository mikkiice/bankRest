package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.AuthRequest;
import com.example.bankcards.dto.request.RefreshTokenRequest;
import com.example.bankcards.dto.request.RegisterRequest;
import com.example.bankcards.dto.response.AuthResponse;
import com.example.bankcards.dto.response.TokenResponse;
import com.example.bankcards.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public AuthResponse login(AuthRequest loginRequest) {
        return null;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public TokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }

    @Override
    public void logout(String refreshToken) {

    }
}
