package com.example.practical_test.controller;

import com.example.practical_test.exceptionHandler.exception.UserAgeException;
import com.example.practical_test.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Value("${minimum.age}")
    private int minimumAge;
    private List<User> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        try {
            if (calculateAge(user.getBirthDate()) < minimumAge) {
                throw new UserAgeException("User must be at least " + minimumAge + " years old.");
            }
            users.add(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (UserAgeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable int userId, @RequestBody User user) {
        try {
            if (userId >= 0 && userId < users.size()) {
                users.get(userId).update(user);
                return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
            }
            throw new RuntimeException("User with id = " + userId + " not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateAllFields(@PathVariable int userId, @RequestBody User user) {
        try {
            if (userId >= 0 && userId < users.size()) {
                users.set(userId, user);
                return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
            }
            throw new RuntimeException("User with id = " + userId + " not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        try {
            if (userId >= 0 && userId < users.size()) {
                users.remove(userId);
                return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
            }
            throw new RuntimeException("User with id = " + userId + " not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByBirthDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        try {
            if (fromDate.isAfter(toDate)) {
                throw new IllegalArgumentException("Invalid date range");
            }
            List<User> result = new ArrayList<>();
            for (User user : users) {
                if (user.getBirthDate().isAfter(fromDate) && user.getBirthDate().isBefore(toDate)) {
                    result.add(user);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    // Helper method to calculate age from birthdate
    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
