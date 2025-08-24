package com.example.bankcards.exception;


import lombok.experimental.StandardException;

@StandardException
public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {}
}
