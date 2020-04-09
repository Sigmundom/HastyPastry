package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.ScreenManager;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Match match;
    private Player player;
    private Player opponent;
    private Level level;
    private List<WorldObject> worldObjects;
    private boolean playerIsChallenger;

    public Game(Match match, boolean playerIsChallenger) {
        this.match = match;
        this.playerIsChallenger = playerIsChallenger;
        this.level = new Level(match.getLevel());
        if (playerIsChallenger) {
            this.player = new Player(match.getChallengerName(), this.level, true);
            this.opponent = new Player(match.getChallengedName(), this.level, false); //Makes deep copy of waffle
        } else {
            this.player = new Player(match.getChallengedName(), this.level, true);
            this.opponent = new Player(match.getChallengerName(), this.level, false);
        }
        worldObjects = new ArrayList<>();
        worldObjects.addAll(level.getObstacles());
        worldObjects.add(this.player.getWaffle());
        worldObjects.add(this.opponent.getWaffle());
        worldObjects.add(level.getGoal());
        // Add boundaries to the level
        worldObjects.add(new SquareObstacle(-2 - this.player.getWaffle().getSprite().getWidth(), 16, 2, 32, true));
        worldObjects.add(new SquareObstacle(20 + this.player.getWaffle().getSprite().getWidth(), 16, 2, 32, true));
        worldObjects.add(new SquareObstacle(9, 33, 18, 2, false));
        worldObjects.add(new SquareObstacle(9, -1, 18, 2, false));
    }

    public Game(Level level) {
        this.player = new Player(level);
        this.level = level;
        worldObjects = new ArrayList<>();
        worldObjects.addAll(level.getObstacles());
        worldObjects.add(this.player.getWaffle());
        worldObjects.add(level.getGoal());
        // Add boundaries to the level
        worldObjects.add(new SquareObstacle(-2 - this.player.getWaffle().getSprite().getWidth(), 16, 2, 32, true));
        worldObjects.add(new SquareObstacle(20 + this.player.getWaffle().getSprite().getWidth(), 16, 2, 32, true));
        worldObjects.add(new SquareObstacle(9, 33, 18, 2, false));
        worldObjects.add(new SquareObstacle(9, -1, 18, 2, false));
    }

    public void initPlayView(World world) {
        for (WorldObject object : worldObjects) {
            object.addBody(world);
        }
        player.getDrawing().addBody(world);

        if (isMultiplayer()) {
            opponent.getDrawing().addBody(world);
        }
    }

    public void update() {
        player.getWaffle().update();
        if (isMultiplayer()) {
            opponent.getWaffle().update();
        }
    }

    public boolean isMultiplayer() {
        return match != null;
    }

    public Match getMatch() {
        return match;
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

    public void ready() {
        DBManager.instance.getDB().ready(this);
    }

    public void receivedDrawing(List<List<String>> opponentDrawing) {
        System.out.println("Recieved drawing!");
        opponent.getDrawing().deserializeDrawing(opponentDrawing);
        ScreenManager.getInstance().showScreen(ScreenEnum.PLAY, this);
    }

    public boolean playerIsChallenger() {
        return playerIsChallenger;
    }
}
