package com.pragma.users.api.infrastructure.exception;

public class SignUpException extends RuntimeException {
    public SignUpException(String message, Throwable cause) {
        super(message, cause);
    }
}