package com.example.regester.models;

public class Route {
    String agencyName;
    String journeyDate;
    String journeyTime;
    String sourceCity, destinationCity;
    String journeyID, agencyID;


    public String getJourneyID() {
        return journeyID;
    }
    public void setJourneyID(String journeyID) {
        this.journeyID = journeyID;
    }
    public String getAgencyID() {
        return agencyID;
    }
    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }



    public Route() {}

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
    public String getSourceCity() {
        return sourceCity;
    }
    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }
    public String getDestinationCity() {
        return destinationCity;
    }
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }


}
