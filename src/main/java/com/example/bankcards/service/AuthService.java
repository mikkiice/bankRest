package com.example.bankcards.service;

import com.example.bankcards.dto.request.AuthRequest;
import com.example.bankcards.dto.request.RefreshTokenRequest;
import com.example.bankcards.dto.request.RegisterRequest;
import com.example.bankcards.dto.response.AuthResponse;
import com.example.bankcards.dto.response.TokenResponse;

public interface AuthService {
    AuthResponse login(AuthRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
    TokenResponse refresh(RefreshTokenRequest refreshTokenRequest);
    void logout(String refreshToken);
}
