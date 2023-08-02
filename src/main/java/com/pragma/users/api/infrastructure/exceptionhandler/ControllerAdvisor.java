package com.pragma.users.api.infrastructure.exceptionhandler;

import com.pragma.users.api.infrastructure.exception.BadRequestException;
import com.pragma.users.api.infrastructure.exception.RepositoryException;
import com.pragma.users.api.infrastructure.exception.SignInException;
import com.pragma.users.api.infrastructure.exception.SignUpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";
    private final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException badRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(handle(badRequestException.getMessage(), badRequestException));
    }

    @ExceptionHandler(RepositoryException.class)
    public ResponseEntity<Map<String, String>> handleRepositoryException(RepositoryException repositoryException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(handle(repositoryException.getMessage(), repositoryException));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException validationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(handle(validationException.getCause().getMessage(), validationException));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception exception, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(handle(bindingResult.getFieldError().getDefaultMessage(), exception));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(handle(exception.getMessage(), exception));
    }

    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<Map<String, String>> handleSignUpException(SignUpException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(handle(exception.getMessage(), exception));
    }

    @ExceptionHandler(SignInException.class)
    public ResponseEntity<Map<String, String>> handleSignInException(SignInException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(handle(exception.getMessage(), exception));
    }

    private Map<String, String> handle(String message, Exception exception) {
        logger.error(message, exception);
        return Collections.singletonMap(MESSAGE, message);
    }
}