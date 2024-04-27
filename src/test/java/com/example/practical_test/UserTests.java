package com.example.practical_test;

import com.example.practical_test.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void createUser_ValidUser_NoValidationErrors() {
        // Arrange
        User user = new User(
                "test@example.com",
                "John",
                "Doe",
                LocalDate.now().minusYears(20),
                null,
                "1234567890"
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    public void createUser_InvalidEmail_ValidationErrors() {
        // Arrange
        User user = new User(
                "invalidemail",  // Invalid email format
                "John",
                "Doe",
                LocalDate.now().minusYears(20),
                null,
                "1234567890"
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Invalid email format", violation.getMessage());
    }

    @Test
    public void createUser_InvalidPhoneNumber_ValidationErrors() {
        User user = new User(
                "test@example.com",
                "John",
                "Doe",
                LocalDate.now().minusYears(20),
                null,
                "123"  // Invalid phone number format
        );

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Invalid phone number", violation.getMessage());
    }
}