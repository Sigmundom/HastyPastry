package com.mygdx.hastypastry.models;

import java.util.List;

public class User {
    private String FBID;
    private String name;
    private String status;
    private Match challenge; // Null if you are the challanger.
    private List<List<String>> drawing;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {};

    public User(String name) {
        this.name = name;
        status = "ready";
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Match getChallenger() {
        return challenge;
    }

    public void setChallenger(Match challenger) {
        this.challenge = challenge;
    }

    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }
}
