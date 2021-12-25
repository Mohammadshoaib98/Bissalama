package com.example.regester.models;

import java.util.HashMap;

public class Agency extends User {

    HashMap<String, Object> clerks;
    HashMap<String, Object> journeys = new HashMap<>();

    public Agency(HashMap<String, Object> clerks, HashMap<String, Object> journeys) {
        this.clerks = clerks;
        this.journeys = journeys;
    }

    public Agency(String userName, String userEmail, String userPhoneNumber, String userID, String userType, HashMap<String, Object> clerks, HashMap<String, Object> journeys) {
        super(userName, userEmail, userPhoneNumber, userID, userType);
        this.clerks = clerks;
        this.journeys = journeys;
    }

    public HashMap<String, Object> getClerks() {
        return clerks;
    }

    public HashMap<String, Object> getJourneys() {
        return journeys;
    }

    public void setClerks(HashMap<String, Object> clerks) {
        this.clerks = clerks;
    }

    public void setJourneys(HashMap<String, Object> journeys) {
        this.journeys = journeys;
    }
}
