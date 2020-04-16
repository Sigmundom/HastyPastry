package com.mygdx.hastypastry.models;

import com.mygdx.hastypastry.ui.StyledTextButton;

import java.util.List;

public class User {
    private String FBID;
    private String name;
    private String status;
    private Match challenge; // Null if you are the challanger.
    private List<List<String>> drawing;
    private float newestHighScore;
    private StyledTextButton userButton;

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

    public void setNewestHighScore(float newestHighScore) { this.newestHighScore = newestHighScore; }

    public float getNewestHighScore() { return newestHighScore; }

    protected StyledTextButton getUserButton() {
        return userButton;
    }

    protected void setUserButton(StyledTextButton userButton) {
        this.userButton = userButton;
    }

}
