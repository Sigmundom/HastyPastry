package com.mygdx.hastypastry.models;

public class User {
    private String name;
    private boolean ready;
    private String challenger;

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
}
