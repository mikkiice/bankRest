package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;


@Schema(description = "Запрос на регистрацию")
public record RegisterRequest(
        @Schema(description = "Логин", example = "malik")
        @NotBlank(message = "Логин не должен быть пустым")
        String username,

        @Schema(description = "Пароль", example = "P@ssw0rd123")
        @NotBlank(message = "Пароль не должен быть пустым")
        String password
) {}
