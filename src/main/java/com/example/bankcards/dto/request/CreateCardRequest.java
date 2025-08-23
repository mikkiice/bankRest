package com.example.bankcards.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateCardRequest(
        @NotNull
        Long userId,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yyyy")
        LocalDate expirationDate
) {
}
