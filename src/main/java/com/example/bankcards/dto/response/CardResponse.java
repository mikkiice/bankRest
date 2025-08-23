package com.example.bankcards.dto.response;

import com.example.bankcards.entity.CardStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CardResponse(
        Long id,
        String username,
        String cardNumber,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
        LocalDate expirationDate,
        CardStatus cardStatus,
        BigDecimal balance

){
}
