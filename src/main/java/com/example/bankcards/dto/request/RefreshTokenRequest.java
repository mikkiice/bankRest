package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на обновление токенов")
public record RefreshTokenRequest(
        @Schema(description = "Refresh JWT", example = "sfjhsdaf8s7adf8asdf76dsa7d...")
        @NotBlank(message = "Refresh-токен обязателен")
        String refreshToken
) {}
