package com.example.regester.models;

public class Journey {
    String agencyName;
    String journeyDate;
    String journeyTime;
    String seat;
    String sourceCity, destinationCity;
    String[] passengersIds = new String[8];

    public Journey(){

    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
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

    public Journey(String agencyName, String journeyDate, String journeyTime, String sourceCity, String destinationCity, String seat) {
        this.agencyName = agencyName;
        this.journeyDate = journeyDate;
        this.journeyTime = journeyTime;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.seat=seat;
    }


    public String[] getPassengersIds() {
        return passengersIds;
    }
    public void setPassengersIds(String[] passengersIds) {
        this.passengersIds = passengersIds;
    }
    public String getSourceCity() { return sourceCity; }
    public String getDestinationCity() { return destinationCity; }
    public String getAgencyName() { return agencyName; }
    public String getJourneyDate() { return journeyDate; }
    public String getJourneyTime() {
        return journeyTime;
    }


}
