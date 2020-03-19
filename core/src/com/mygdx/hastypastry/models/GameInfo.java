package com.mygdx.hastypastry.models;

public class GameInfo {
    private String gameID;
    private String player1;
    private String player2;

    public GameInfo(String gameID, String player1, String player2) {
        this.gameID = gameID;
        this.player1 = player1;
        this.player2 = player2;
    }

    public String getGameID() {
        return gameID;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

//    public ObjectMap<String, WorldObject> getWorldObjects() {
//        return worldObjects;
//    }
//
//    private void loadLevel(Level level) {
//        worldObjects.put("waffle", level.getWaffle());
//        int i = 0;
//        for (WorldObject obstacle : level.getObstacles()) {
//            worldObjects.put("obstacle"+i, obstacle);
//        }
//        // worldobjects.put("goal", level.getGoal());
//    }
}
