package com.example.bankcards.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String s) {}
}
