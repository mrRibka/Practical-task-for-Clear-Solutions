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

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testUpdateWithValidUser() {
        // Arrange
        User originalUser = new User("original@example.com", "John", "Doe",
                LocalDate.of(1990, 5, 15), "123 Main St", "1234567890");

        User newUser = new User("new@example.com", "Jane", "Doe",
                LocalDate.of(1995, 8, 20), "456 Elm St", "9876543210");

        // Act
        originalUser.update(newUser);

        // Assert
        assertEquals("new@example.com", originalUser.getEmail());
        assertEquals("Jane", originalUser.getFirstName());
        assertEquals("Doe", originalUser.getLastName());
        assertEquals(LocalDate.of(1995, 8, 20), originalUser.getBirthDate());
        assertEquals("456 Elm St", originalUser.getAddress());
        assertEquals("9876543210", originalUser.getPhoneNumber());
    }

    @Test
    void testUpdateWithNullFields() {
        // Arrange
        User originalUser = new User("original@example.com", "John", "Doe",
                LocalDate.of(1990, 5, 15), "123 Main St", "1234567890");

        User newUser = new User(null, null, null,
                null, null, null);

        // Act
        originalUser.update(newUser);

        // Assert
        assertEquals("original@example.com", originalUser.getEmail());
        assertEquals("John", originalUser.getFirstName());
        assertEquals("Doe", originalUser.getLastName());
        assertEquals(LocalDate.of(1990, 5, 15), originalUser.getBirthDate());
        assertEquals("123 Main St", originalUser.getAddress());
        assertEquals("1234567890", originalUser.getPhoneNumber());
    }

    @Test
    void testUpdateWithInvalidUser() {
        // Arrange
        User originalUser = new User("original@example.com", "John", "Doe",
                LocalDate.of(1990, 5, 15), "123 Main St", "1234567890");

        User invalidUser = new User("", "", "",
                LocalDate.of(2025, 1, 1), "", "abc123");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> originalUser.update(invalidUser));
    }
}