package com.example.regester.models;

public class Clerk extends User {
    String agencyId,clerkId;

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public void setClerkId(String clerkId) {
        this.clerkId = clerkId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public String getClerkId() {
        return clerkId;
    }

    public Clerk(String fullName, String email, String phone, String userId, String agencyId, String clerkId) {
        super(fullName, email, phone, userId);
        this.agencyId = agencyId;
        this.clerkId = clerkId;
    }
}
