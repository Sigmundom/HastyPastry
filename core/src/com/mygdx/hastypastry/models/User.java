package com.mygdx.hastypastry.models;

import java.util.List;

public class User {
    private String FBID;
    private String name;
    private boolean ready;
    private Match challenge; // Null if you are the challanger.
    private List<List<String>> drawing;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {};

    public User(String name) {
        this.name = name;
        ready = true;
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Match getChallenge() {
        return challenge;
    }

    public void setChallenge(Match challenge) {
        this.challenge = challenge;
    }

    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }
}
