package com.example.regester.models;

public class AgencyUser extends User {
    String subscriptionCode;
    String subscriptionDate;
    boolean isSubscriptionExpired;

    public String getSubscriptionCode() {
        return subscriptionCode;
    }

    public void setSubscriptionCode(String subscriptionCode) {
        this.subscriptionCode = subscriptionCode;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public boolean isSubscriptionExpired() {
        return isSubscriptionExpired;
    }

    public void setSubscriptionExpired(boolean subscriptionExpired) {
        isSubscriptionExpired = subscriptionExpired;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    String agencyId;

    public AgencyUser(String fullName, String email, String phone, String userId, String subscriptionCode, String subscriptionDate, boolean isSubscriptionExpired, String agencyId) {
        super(fullName, email, phone, userId);
        this.subscriptionCode = subscriptionCode;
        this.subscriptionDate = subscriptionDate;
        this.isSubscriptionExpired = isSubscriptionExpired;
        this.agencyId = agencyId;
    }
}
