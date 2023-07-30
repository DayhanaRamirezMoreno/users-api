package com.pragma.users.api.infrastructure.exception;

public class SignInException extends RuntimeException{
    public SignInException(String message, Throwable cause) {
        super(message, cause);
    }
}

