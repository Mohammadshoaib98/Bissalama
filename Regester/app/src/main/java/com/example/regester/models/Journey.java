package com.example.regester.models;

public class Journey {
    String agencyName;
    String journeyDate;
    String journeyTime;
    String seat;
    String citySource, cityDestination;
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

    public void setCitySource(String citySource) {
        this.citySource = citySource;
    }

    public void setCityDestination(String cityDestination) {
        this.cityDestination = cityDestination;
    }

    public Journey(String agencyName, String journeyDate, String journeyTime, String citySource, String cityDestination,String seat) {
        this.agencyName = agencyName;
        this.journeyDate = journeyDate;
        this.journeyTime = journeyTime;
        this.citySource = citySource;
        this.cityDestination = cityDestination;
        this.seat=seat;
    }


    public String[] getPassengersIds() {
        return passengersIds;
    }
    public void setPassengersIds(String[] passengersIds) {
        this.passengersIds = passengersIds;
    }
    public String getCitySource() { return citySource; }
    public String getCityDestination() { return cityDestination; }
    public String getAgencyName() { return agencyName; }
    public String getJourneyDate() { return journeyDate; }
    public String getJourneyTime() {
        return journeyTime;
    }


}
