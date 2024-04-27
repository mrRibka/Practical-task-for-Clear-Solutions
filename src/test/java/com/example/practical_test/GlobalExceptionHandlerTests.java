package com.example.practical_test;

import com.example.practical_test.exceptionHandler.GlobalExceptionHandler;
import com.example.practical_test.exceptionHandler.exception.UserAgeException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTests {

    @Test
    public void handleUserAgeException_ValidException_ReturnsBadRequest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        UserAgeException ex = new UserAgeException("User must be at least 18 years old.");

        ResponseEntity<String> response = exceptionHandler.handleUserAgeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User must be at least 18 years old.", response.getBody());
    }

    @Test
    public void handleIllegalArgumentException_ValidException_ReturnsBadRequest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        IllegalArgumentException ex = new IllegalArgumentException("Invalid date range");

        ResponseEntity<String> response = exceptionHandler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid date range", response.getBody());
    }
}

