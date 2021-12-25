package com.example.regester.models;

public class User {
    private String fullName;
    private String email;

    public User(String fullName, String email, String phone, String userId) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.userId = userId;
    }

    private String phone;
    String userId;


    public User() {

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
