package com.example.bankcards.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        String errorCode,
        LocalDateTime errorDate
) {
}
