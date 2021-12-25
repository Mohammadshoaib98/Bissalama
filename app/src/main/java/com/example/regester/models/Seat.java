package com.example.regester.models;

public class Seat {
    int seatNumber;
    String seatID;
    boolean seatStateEnabled;
    boolean seatStateChecked;
    String passengerID;
    String journeyID;


    public Seat() {
    }

    public Seat(int seatNumber, String seatID, boolean seatStateEnabled, boolean seatStateChecked, String passengerID, String journeyID) {
        this.seatNumber = seatNumber;
        this.seatID = seatID;
        this.seatStateEnabled = seatStateEnabled;
        this.seatStateChecked = seatStateChecked;
        this.passengerID = passengerID;
        this.journeyID = journeyID;
    }

    public String getSeatID() {
        return seatID;
    }

    public void setSeatID(String seatID) {
        this.seatID = seatID;
    }

    public boolean isSeatStateEnabled() {
        return seatStateEnabled;
    }

    public void setSeatStateEnabled(boolean seatStateEnabled) {
        this.seatStateEnabled = seatStateEnabled;
    }

    public boolean isSeatStateChecked() {
        return seatStateChecked;
    }


    public String getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(String journeyID) {
        this.journeyID = journeyID;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setSeatStateChecked(boolean seatStateChecked) {
        this.seatStateChecked = seatStateChecked;
    }

    public String getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(String passengerID) {
        this.passengerID = passengerID;
    }


}

