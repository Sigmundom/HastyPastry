package com.mygdx.hastypastry.models;

import java.util.List;

public class Match {
    private String matchID; //Challenger's ID is used as matchID
    private String challengerName;
    private String challengedName;
    private List<List<String>> challengerDrawing;
    private List<List<String>> challengedDrawing;
    private String level;

    // Default constructor required for calls to DataSnapshot.getValue(Match.class)
    public Match() {}

    public Match(String matchID, String challengerName, String challengedName) {
        this.matchID = matchID;
        this.challengerName = challengerName;
        this.challengedName = challengedName;
    }

    public String getMatchID() {
        return matchID;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getChallengerName() {
        return challengerName;
    }

    public String getChallengedName() {
        return challengedName;
    }

    public List<List<String>> getChallengerDrawing() {
        return challengerDrawing;
    }

    public void setChallengerDrawing(List<List<String>> challengerDrawing) {
        this.challengerDrawing = challengerDrawing;
    }

    public List<List<String>> getChallengedDrawing() {
        return challengedDrawing;
    }

    public void setChallengedDrawing(List<List<String>> challengedDrawing) {
        this.challengedDrawing = challengedDrawing;
    }
}
