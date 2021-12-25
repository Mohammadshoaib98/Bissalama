package com.example.regester.models;

public class PassJourneys {

    String agencyName;
    String journeyDate;
    String journeyTime;
    String citySource, cityDestination;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public String getCitySource() {
        return citySource;
    }

    public void setCitySource(String citySource) {
        this.citySource = citySource;
    }

    public String getCityDestination() {
        return cityDestination;
    }

    public void setCityDestination(String cityDestination) {
        this.cityDestination = cityDestination;
    }




    public PassJourneys(String agencyName, String journeyDate, String journeyTime, String citySource, String cityDestination) {
        this.agencyName = agencyName;
        this.journeyDate = journeyDate;
        this.journeyTime = journeyTime;
        this.citySource = citySource;
        this.cityDestination = cityDestination;
    }





}
