package com.example.regester.models;

public class Route {
    String agencyName;
    String journeyDate;
    String journeyTime;
    String citySource, cityDestination;
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


}
