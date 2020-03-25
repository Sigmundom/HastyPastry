package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.levels.Level;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String gameID;
    private Player player;
    private Player opponent;
    private List<WorldObject> worldObjects;
    private List<List<Vector2>> finalLines;

    public Game(String gameID, String player, String opponent, Level level) {
        this.gameID = gameID;
        this.player = new Player(player, level.getWaffle());
        this.opponent = new Player(opponent, new Waffle(level.getWaffle())); //Makes deep copy of waffle
        worldObjects = new ArrayList<>();
        worldObjects.addAll(level.getObstacles());
        worldObjects.add(this.player.getWaffle());
        worldObjects.add(this.opponent.getWaffle());
        worldObjects.add(level.getGoal());
    }

    public Game(Level level) {
        this.player = new Player(level.getWaffle());
        worldObjects = new ArrayList<>();
        worldObjects.addAll(level.getObstacles());
        worldObjects.add(this.player.getWaffle());
        worldObjects.add(level.getGoal());
    }

    public void initPlayView(World world) {
        for (WorldObject object : worldObjects) {
            object.addBody(world);
        }
        player.getDrawing().addBody(world);

        finalLines = new ArrayList<>();
        finalLines.addAll(player.getDrawing().getLines());

        if (isMultiplayer()) {
            opponent.getDrawing().addBody(world);
            finalLines.addAll(opponent.getDrawing().getLines());
        }
    }

    public void update() {
        player.getWaffle().update();
        if (isMultiplayer()) {
            opponent.getWaffle().update();
        }
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

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public List<List<Vector2>> getFinalLines() {
        return finalLines;
    }
}
