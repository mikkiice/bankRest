package com.example.bankcards.exception;

import lombok.experimental.StandardException;

@StandardException
public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {}
}
