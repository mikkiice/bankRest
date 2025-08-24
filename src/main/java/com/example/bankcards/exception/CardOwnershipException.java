package com.example.bankcards.exception;

import lombok.experimental.StandardException;

@StandardException
public class CardOwnershipException extends RuntimeException {
    public CardOwnershipException(String message) {}
}
