package com.example.bankcards.dto.response;

import com.example.bankcards.entity.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(
        Long id,
        String fromCardNumber,
        String toCardNumber,
        BigDecimal amount,
        LocalDate date,
        TransactionStatus status
) {
}
