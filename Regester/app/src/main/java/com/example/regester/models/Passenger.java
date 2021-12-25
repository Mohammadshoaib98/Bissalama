package com.example.regester.models;

public class Passenger extends User {
    private String gender;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private double balance;

    public Passenger(String fullName, String email, String phone, String userId, String gender, double balance) {
        super(fullName, email, phone, userId);
        this.gender = gender;
        this.balance = balance;
    }
}
