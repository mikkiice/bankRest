package com.example.bankcards.exception;


import lombok.experimental.StandardException;

@StandardException
public class CardAlreadyExistsException extends RuntimeException {
    public CardAlreadyExistsException(String message) {}
}
