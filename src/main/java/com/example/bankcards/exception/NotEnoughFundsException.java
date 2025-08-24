package com.example.bankcards.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(String message) {}
}
