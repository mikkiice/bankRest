package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateTransactionRequest(
        @NotNull
        Long fromCardId,
        @NotNull
        Long toCardId,
        @NotNull
        @Positive
        BigDecimal amount
) {
}
