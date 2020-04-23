package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.models.dbmodels.Match;
import com.mygdx.hastypastry.models.dbmodels.User;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.ScreenManager;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private com.mygdx.hastypastry.models.dbmodels.Match match;
    private Player player;
    private Player opponent;
    private Level level;
    private List<WorldObject> worldObjects;
    private boolean playerIsChallenger;
    private String result = "";
    private String message;
    private Lobby lobby;
    private LeaderBoard leaderBoard;

    public Game(com.mygdx.hastypastry.models.dbmodels.Match match, boolean playerIsChallenger, Lobby lobby) {
        this.match = match;
        this.playerIsChallenger = playerIsChallenger;
        this.level = new Level(match.getLevel());
        this.lobby = lobby;
        if (playerIsChallenger) {
            this.player = new Player(match.getChallenger().getName(), this.level, true);
            this.opponent = new Player(match.getChallenged().getName(), this.level, false); //Makes deep copy of waffle
        } else {
            this.player = new Player(match.getChallenged().getName(), this.level, true);
            this.opponent = new Player(match.getChallenger().getName(), this.level, false);
        }
        worldObjects = new ArrayList<>();
        worldObjects.addAll(level.getObstacles());
        worldObjects.add(this.player.getWaffle());
        worldObjects.add(this.opponent.getWaffle());
        worldObjects.add(level.getGoal());
        // Add boundaries to the level
        worldObjects.add(new SquareObstacle(-2 - this.player.getWaffle().getSprite().getWidth(), 16, 2, 50, true));
        worldObjects.add(new SquareObstacle(20 + this.player.getWaffle().getSprite().getWidth(), 16, 2, 50, true));
        worldObjects.add(new SquareObstacle(9, 33, 18, 2, false));
        worldObjects.add(new SquareObstacle(9, -1, 18, 2, false));

        //Initializing the leaderboard forgiven level.
        leaderBoard = new LeaderBoard(this);
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
        // Updates waffle(s)
        player.getWaffle().update();
        if (isMultiplayer()) {
            opponent.getWaffle().update();
        }

        // Check if waffle(s) is stuck
        if (isMultiplayer()) {
            if (opponent.getWaffle().WaffleHasStopped()) {
                // Opponent died
                opponentDied();
            }

            if (player.getWaffle().WaffleHasStopped()) {
                // You died
                playerDied("You waffle is stuck!");
            }
        } else {
            // Singleplayer
            if (player.getWaffle().WaffleHasStopped()) {
                // You died
                setMessage("Your waffle is stuck!");
                gameOver();
            }
        }
    }

    public void opponentDied() {
        opponent.getWaffle().setIsDead();
        if (player.getWaffle().isDead()) {
            // Both players are dead
            gameOver();
        }
    }

    public void playerDied(String message) {
        player.getWaffle().setIsDead();
        setMessage(message);
        setResult("You lost!");
        if (opponent.getWaffle().isDead()) {
            // Both players are dead
            gameOver();
        }
    }

    public void gameOver() {
        setResult(result);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isMultiplayer()) {
            ScreenManager.getInstance().showScreen(ScreenEnum.COMPLETED_MULTIPLAYER, this);
        } else {
            ScreenManager.getInstance().showScreen(ScreenEnum.FAILED_LEVEL, this);
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

    /**
     * Called when player is done drawing in DrawView.
     * Depending on the multiplayer or not, the method will say
     * that the player is ready in the match and waiting for the other to complete.
     */
    public void ready() {
        if (isMultiplayer()) {
            player.setReady();
            DBManager.instance.getDB().ready(this);
            if (opponent.isReady()) {
               play();
            }
        } else {
            play();
        }
    }

    private void play() {
        ScreenManager.getInstance().showScreen(ScreenEnum.PLAY, this);
    }

    public void receivedDrawing(List<List<String>> opponentDrawing) {
        if (!opponent.isReady()) {
            System.out.println("Received drawing!");
            opponent.setReady();
            opponent.getDrawing().deserializeDrawing(opponentDrawing);
            if (player.isReady()) {
                play();
            }
        } else {
            System.out.println("Received drawing AGAIN!");
        }
    }

    public boolean playerIsChallenger() {
        return playerIsChallenger;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public User getPlayerUser() {
        return playerIsChallenger ? match.getChallenger() : match.getChallenged();
    }

    public User getOpponentUser() {
        return playerIsChallenger ? match.getChallenged() : match.getChallenger();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LeaderBoard getLeaderBoard() { return leaderBoard; }
}
