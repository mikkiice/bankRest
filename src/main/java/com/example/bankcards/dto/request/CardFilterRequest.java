package com.example.bankcards.dto.request;

import com.example.bankcards.entity.CardStatus;

import java.math.BigDecimal;

public record CardFilterRequest(
        BigDecimal minBalance,
        BigDecimal maxBalance,
        CardStatus cardStatus
) {
}
