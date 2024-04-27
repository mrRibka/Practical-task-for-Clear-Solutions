package com.example.practical_test.exceptionHandler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAgeException extends RuntimeException {

    public UserAgeException(String message) {
        super(message);
    }
}
