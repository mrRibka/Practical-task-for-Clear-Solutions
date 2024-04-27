package com.example.practical_test.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class User {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    private String address;
    @Pattern(regexp="(^$|[0-9]{10})", message = "Invalid phone number")
    private String phoneNumber;

    // Constructors, getters, setters, and other methods

    public User() {
    }

    public User(String email, String firstName, String lastName, LocalDate birthDate, String address, String phoneNumber) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void update(User user) {
        if (user.getEmail() != null) this.email = user.getEmail();
        if (user.getFirstName() != null) this.firstName = user.getFirstName();
        if (user.getLastName() != null) this.lastName = user.getLastName();
        if (user.getBirthDate() != null) this.birthDate = user.getBirthDate();
        if (user.getAddress() != null) this.address = user.getAddress();
        if (user.getPhoneNumber() != null) this.phoneNumber = user.getPhoneNumber();
    }

    // Getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}