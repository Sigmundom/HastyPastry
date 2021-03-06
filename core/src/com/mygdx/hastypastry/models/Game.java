package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.models.dbmodels.Match;
import com.mygdx.hastypastry.models.dbmodels.User;
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
    private String result = "";
    private String message;
    private Lobby lobby;
    private LeaderBoard leaderBoard;

    public Game(Match match, boolean playerIsChallenger, Lobby lobby) {
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
        worldObjects.add(new SquareObstacle(9, 33, 18, 2, "normal"));
        worldObjects.add(new SquareObstacle(9, -1, 18, 2, "normal"));

        //Initializing the leaderboard for the given level.
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
        worldObjects.add(new SquareObstacle(9, 33, 18, 2, "normal"));
        worldObjects.add(new SquareObstacle(9, -1, 18, 2, "normal"));
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

        // Check if waffle(s) is / are stuck or out of bounds
        if (isMultiplayer()) {
            if (opponent.getWaffle().waffleHasStopped()) {
                // Opponent died
                opponentDied();
            }

            if (player.getWaffle().waffleHasStopped()) {
                // You died
                playerDied("Your waffle is stuck!");
            }
            if ((player.getWaffle().getPosition().x < - player.getWaffle().getRadius() * 2) || (player.getWaffle().getPosition().x > Config.WORLD_WIDTH + player.getWaffle().getRadius())) {
                playerDied("Out of bounds!");
            }
            if ((opponent.getWaffle().getPosition().x < - opponent.getWaffle().getRadius() * 2) || (opponent.getWaffle().getPosition().x > Config.WORLD_WIDTH + opponent.getWaffle().getRadius())) {
                // Opponent died
                opponentDied();
            }
        } else {
            // Singleplayer
            if (player.getWaffle().waffleHasStopped()) {
                // You died
                setMessage("Your waffle is stuck!");
                gameOver();
            }
            // Check if waffle is out of bounds
            if ((player.getWaffle().getPosition().x < - player.getWaffle().getRadius() * 2) || (player.getWaffle().getPosition().x > Config.WORLD_WIDTH + player.getWaffle().getRadius())) {
                // You died
                setMessage("Out of bounds!");
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
            opponent.getDrawing().deserializeLines(opponentDrawing);
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
