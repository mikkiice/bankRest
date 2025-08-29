package com.example.bankcards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(description = "Ответ с токенами")
public record TokenResponse(
        @Schema(description = "Тип токена", example = "Bearer")
        String tokenType,

        @Schema(description = "Access JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "Refresh JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String refreshToken,

        @Schema(description = "Момент истечения access-токена (UTC)", example = "2025-08-29T18:45:00Z")
        Instant expiresAt
) {
    public static TokenResponse bearer(String accessToken, String refreshToken, Instant expiresAt) {
        return new TokenResponse("Bearer", accessToken, refreshToken, expiresAt);
    }
}
