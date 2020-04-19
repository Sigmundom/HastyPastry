package com.mygdx.hastypastry.models;

import com.mygdx.hastypastry.singletons.DBManager;

import java.util.HashMap;
import java.util.Map;

public class LeaderBoard {
    private Game game;
    private boolean change;
    private Map<String, Float> leaderBoard = new HashMap<>();

    public LeaderBoard(Game game) {
        this.game = game;
        change = false;
    }

    public void updateLeaderBoard() {
        if(game.getResult() == "You won!") {
            DBManager.instance.getDB().updateLeaderBoard(game);
        }
    }

    public void setLeaderBoard(String name, float time) {
        leaderBoard.put(name, time);
    }

    public Map<String, Float> getLeaderBoard() { return leaderBoard; }

    public void setChange(boolean change) { this.change = change; }

    public boolean getChange() { return change; }
}
