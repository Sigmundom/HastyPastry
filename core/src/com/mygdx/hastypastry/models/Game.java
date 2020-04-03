package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.levels.Level;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String matchID;
    private Player player;
    private Player opponent;
    private Level level;
    private List<WorldObject> worldObjects;
    private List<List<Vector2>> finalLines;

    public Game(Match match, boolean challenger) {
        this.matchID = match.getMatchID();
        this.level = new Level(match.getLevel());
        if (challenger) {
            this.player = new Player(match.getChallengerName(), this.level);
            this.opponent = new Player(match.getOpponentName(), this.level); //Makes deep copy of waffle
        } else {
            this.player = new Player(match.getOpponentName(), this.level);
            this.opponent = new Player(match.getChallengerName(), this.level);
        }
        worldObjects = new ArrayList<>();
        worldObjects.addAll(level.getObstacles());
        worldObjects.add(this.player.getWaffle());
        worldObjects.add(this.opponent.getWaffle());
        worldObjects.add(level.getGoal());
    }

    public Game(Level level) {
        this.player = new Player(level);
        this.level = level;
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
        return matchID != null;
    }

    public String getMatchID() {
        return matchID;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Level getLevel() { return level; }

    public List<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public List<List<Vector2>> getFinalLines() {
        return finalLines;
    }


}
