package com.example.regester.models;

import android.widget.CheckBox;

public class Seat {


    public Seat( String seatNumber, String seatState, String userID) {
        this.seatNumber = seatNumber;
        this.seatState = seatState;
        this.userID = userID;
    }


    String seatNumber;
    String seatState;

    

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }


    public void setSeatState(String seatState) {
        this.seatState = seatState;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    String userID;







}

