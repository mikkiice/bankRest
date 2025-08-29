package com.example.bankcards.dto.response;

import com.example.bankcards.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String fromCardNumber,
        String toCardNumber,
        BigDecimal amount,
        LocalDateTime createdAt,
        TransactionStatus status
) {
}
