package com.mygdx.hastypastry.models;

import com.mygdx.hastypastry.levels.Level;

import java.util.ArrayList;

public class Game {
    private String gameID;
    private Player player;
    private Player opponent;
    private ArrayList<Obstacle> obstacles;

    public Game(String gameID, String player, String opponent, Level level) {
        this.gameID = gameID;
        this.player = new Player(player, level.getWaffle());
        this.opponent = new Player(opponent, level.getWaffle());
        this.obstacles = level.getObstacles();
    }

    public Game(Level level) {
        this.player = new Player(level.getWaffle());
        this.obstacles = level.getObstacles();
    }

    public boolean isMultiplayer() {
        return gameID != null;
    }

    public String getGameID() {
        return gameID;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}
