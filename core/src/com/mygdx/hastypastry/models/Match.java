package com.mygdx.hastypastry.models;

import java.util.List;

public class Match {
    private String matchID; //Challenger's ID is used as matchID
    private String challengerName;
    private String opponentName;
    private List<List<String>> challengerDrawing;
    private List<List<String>> opponentDrawing;
    private String level;

    // Default constructor required for calls to DataSnapshot.getValue(Match.class)
    public Match() {}

    public Match(String matchID, String challengerName, String opponentName) {
        this.matchID = matchID;
        this.challengerName = challengerName;
        this.opponentName = opponentName;
    }

    public String getChallengerName() {
        return challengerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public List<List<String>> getChallengerDrawing() {
        return challengerDrawing;
    }

    public void setChallengerDrawing(List<List<String>> challengerDrawing) {
        this.challengerDrawing = challengerDrawing;
    }

    public List<List<String>> getOpponentDrawing() {
        return opponentDrawing;
    }

    public void setOpponentDrawing(List<List<String>> opponentDrawing) {
        this.opponentDrawing = opponentDrawing;
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
}
