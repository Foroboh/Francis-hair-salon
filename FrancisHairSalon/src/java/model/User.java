package com.francishairsalon.model;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String passwordHash;
    private String role;           //"customer" or "admin"
    private Timestamp createdAt;

    //This constructor ensures JSP and Servlet can create new objects without errors
    public User() {
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    //A helper method to glue both First and Last name together to display as Full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    //A simple setter to link the plaintext password from the UI to the database passwordHash field
    public void setPassword(String password) {
        this.passwordHash = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{userId=" + userId
                + ", email='" + email + '\''
                + ", role='" + role + '\''
                + '}';
    }
}
