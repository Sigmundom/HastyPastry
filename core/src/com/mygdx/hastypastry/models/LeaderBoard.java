package com.mygdx.hastypastry.models;

import com.mygdx.hastypastry.singletons.DBManager;

public class LeaderBoard {
    private Game game;

    public LeaderBoard(Game game) {
        this.game = game;
    }

    public void updateLeaderBoard() {
        DBManager.instance.getDB().updateLeaderBoard(game);
    }
}
