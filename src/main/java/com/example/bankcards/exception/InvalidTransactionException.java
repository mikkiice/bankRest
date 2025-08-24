package com.example.bankcards.exception;


import lombok.experimental.StandardException;

@StandardException
public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String message) {}
}
