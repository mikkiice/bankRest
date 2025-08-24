package com.example.bankcards.exception;

import lombok.experimental.StandardException;

@StandardException
public class InvalidCardStatusException extends RuntimeException {
    public InvalidCardStatusException(String message) {}
}
