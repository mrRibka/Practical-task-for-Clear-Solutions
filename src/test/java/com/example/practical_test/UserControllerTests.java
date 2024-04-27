package com.example.practical_test;

import com.example.practical_test.controller.UserController;
import com.example.practical_test.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTests {

    @InjectMocks
    @Autowired
    private UserController userController;

    @Test
    public void createUser_ValidUser_ReturnsCreated() {
        User user = new User("test@example.com", "John", "Doe", LocalDate.now().minusYears(20), null, null);
        ResponseEntity<String> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void createUser_UserWithInvalidAge_ReturnsBadRequest() {
        User user = new User("test@example.com", "John", "Doe", LocalDate.now().minusYears(10), null, null);
        ResponseEntity<String> response = userController.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void updateUser_ExistingUser_ReturnsOk() {
        userController.createUser(new User("test1@example.com", "John", "Doe", LocalDate.of(1995, 5, 5), null, null));
        ResponseEntity<String> response = userController.updateUser(0, new User());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateUser_NonExistingUser_ReturnsNotFound() {
        ResponseEntity<String> response = userController.updateUser(0, new User());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateAllFields_ExistingUser_ReturnsOk() {
        userController.createUser(new User("test1@example.com", "John", "Doe", LocalDate.of(1995, 5, 5), null, null));
        ResponseEntity<String> response = userController.updateAllFields(0, new User());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void updateAllFields_NonExistingUser_ReturnsNotFound() {
        ResponseEntity<String> response = userController.updateAllFields(0, new User());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteUser_ExistingUser_ReturnsOk() {
        userController.createUser(new User("test1@example.com", "John", "Doe", LocalDate.of(1995, 5, 5), null, null));
        ResponseEntity<String> response = userController.deleteUser(0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteUser_NonExistingUser_ReturnsNotFound() {
        System.out.println(userController.getAll());
        ResponseEntity<String> response = userController.deleteUser(0);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void searchUsersByBirthDateRange_ValidRange_ReturnsOk() {
        LocalDate fromDate = LocalDate.of(1990, 1, 1);
        LocalDate toDate = LocalDate.of(2000, 1, 1);

        User user1 = new User("test1@example.com", "John", "Doe", LocalDate.of(1995, 5, 5), null, null);
        User user2 = new User("test2@example.com", "Jane", "Doe", LocalDate.of(1992, 7, 10), null, null);
        userController.createUser(user1);
        userController.createUser(user2);

        ResponseEntity<List<User>> response = userController.searchUsersByBirthDateRange(fromDate, toDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void searchUsersByBirthDateRange_InvalidRange_ReturnsBadRequest() {
        LocalDate fromDate = LocalDate.of(2000, 1, 1);
        LocalDate toDate = LocalDate.of(1990, 1, 1);
        ResponseEntity<List<User>> response = userController.searchUsersByBirthDateRange(fromDate, toDate);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
}
