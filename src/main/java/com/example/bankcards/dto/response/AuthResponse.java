package com.example.bankcards.dto.response;

public record AuthResponse(
        String accessToken,
        String tokenType,
        Long   expiresIn,
        String refreshToken,
        String username,
        String role
) {}
