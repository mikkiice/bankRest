package com.example.bankcards.service;

import com.example.bankcards.dto.request.AuthRequest;
import com.example.bankcards.dto.request.RegisterRequest;
import com.example.bankcards.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(AuthRequest loginRequest);
    AuthResponse register(RegisterRequest registerRequest);
}
