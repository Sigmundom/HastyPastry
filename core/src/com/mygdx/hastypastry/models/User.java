package com.mygdx.hastypastry.models;

import java.util.List;

public class User {
    private String FBID;
    private String name;
    private boolean ready;
    private transient User challenger; // Null if you are the challanger.
    private List<List<String>> drawing;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {};

    public User(String name) {
        this.name = name;
        ready = true;
    }

//    public User(String name, boolean isReady, User challenger) {
//        this.name = name;
//        this.ready = isReady;
//        this.challenger = challenger;
//    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public User getChallenger() {
        return challenger;
    }

    public void setChallenger(User challenger) {
        this.challenger = challenger;
    }

    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }
}
