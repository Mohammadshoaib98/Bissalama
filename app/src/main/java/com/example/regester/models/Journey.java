package com.example.regester.models;

import java.util.ArrayList;
import java.util.HashMap;

public class Journey {

    String agencyName;

    String sourceCity;
    String destinationCity;

    String journeyDate;
    String journeyTime;

    HashMap<String,Object> seats = new HashMap<String,Object>();

    public Journey(String agencyName, String sourceCity, String destinationCity, String journeyDate, String journeyTime, HashMap<String, Object> seats) {
        this.agencyName = agencyName;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.journeyDate = journeyDate;
        this.journeyTime = journeyTime;
        this.seats = seats;
    }

    public Journey() {

    }

    public HashMap<String, Object> getSeats() {
        return seats;
    }

    public void setSeats(HashMap<String, Object> seats) {
        this.seats = seats;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }



    public String getSourceCity() {
        return sourceCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public String getJourneyTime() {
        return journeyTime;
    }


}
