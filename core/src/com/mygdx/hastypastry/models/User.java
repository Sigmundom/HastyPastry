package com.mygdx.hastypastry.models;

public class User {
    private String FBID;
    private String name;
    private boolean ready;
    private String challenger;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {};

    public User(String name) {
        this.name = name;
        ready = true;
        challenger = "";
    }

    public User(String name, boolean isReady, String challenger) {
        this.name = name;
        this.ready = isReady;
        this.challenger = challenger;
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

    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }
}