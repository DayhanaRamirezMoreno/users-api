package com.pragma.users.api.infrastructure.exceptionhandler;

import com.pragma.users.api.infrastructure.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

//TODO pendiente crear exception handler para Repository Exception
@ControllerAdvice
public class ControllerAdvisor {

    private static final String MESSAGE = "message";

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(
            NoDataFoundException ignoredNoDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(handle(ExceptionResponse.NO_DATA_FOUND.getMessage()));
    }

    private Map<String, String> handle(String message){
        return Collections.singletonMap(MESSAGE, message);
    }
    
}