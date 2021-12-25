package com.example.regester.models;

public class JourneyUpdated {
    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    String updatedDate, updatedTime;

    public JourneyUpdated(String updatedDate, String updatedTime) {
        this.updatedDate = updatedDate;
        this.updatedTime = updatedTime;
    }
}
