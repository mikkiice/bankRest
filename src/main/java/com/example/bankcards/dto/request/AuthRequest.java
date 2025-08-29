package com.example.bankcards.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(

        @Schema(description = "Логин пользователя", example = "userlogin")
        @NotBlank(message = "Логин не должен быть пустым")
        String username,
        @Schema(description = "Пароль пользователя", example = "P@ssw0rd123")
        @NotBlank(message = "Пароль не должен быть пустым")
        String password
) {
}
