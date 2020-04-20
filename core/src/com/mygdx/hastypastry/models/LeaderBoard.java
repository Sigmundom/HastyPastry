package com.mygdx.hastypastry.models;

import com.mygdx.hastypastry.singletons.DBManager;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderBoard {
    private Game game;
    private boolean change;
    private ArrayList<LeaderBoardEntry> leaderBoard = new ArrayList<>();

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
        leaderBoard.add(new LeaderBoardEntry(name, time));
        Collections.sort(leaderBoard);
    }

    public ArrayList<LeaderBoardEntry> getLeaderBoard() { return leaderBoard; }

    public void setChange(boolean change) { this.change = change; }

    public boolean getChange() { return change; }
}
