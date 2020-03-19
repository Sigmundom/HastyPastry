package com.mygdx.hastypastry.models;

import com.mygdx.hastypastry.levels.Level;

import java.util.ArrayList;

public class Game {
    private String gameID;
    private Player player1;
    private Player player2;
    private ArrayList<Obstacle> obstacles;

    public Game(String gameID, String player1, String player2, Level level) {
        this.gameID = gameID;
        this.player1 = new Player(player1, level.getWaffle());
        this.player2 = new Player(player2, level.getWaffle());
        this.obstacles = level.getObstacles();
    }

    public Game(Level level) {
        this.player1 = new Player(level.getWaffle());
        this.obstacles = level.getObstacles();
    }

    public boolean isMultiplayer() {
        return gameID != null;
    }

    public String getGameID() {
        return gameID;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}
