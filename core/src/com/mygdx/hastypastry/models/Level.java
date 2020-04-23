package com.mygdx.hastypastry.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Obstacle> obstacles = new ArrayList<>();
    private Waffle waffle;
    private Goal goal;
    private int inkLimit;
    private int timeLimit;
    private List<Float> starRank = new ArrayList<>();
    private String level;

    public Level(String level){
        this.level = level;
        int index = Integer.parseInt(level.split(" ")[1])-1;

        JsonReader jsonReader = new JsonReader();
        JsonValue levelData = jsonReader.parse(Gdx.files.internal("levels.json")).get(index);

        JsonValue waffleJson = levelData.get("waffle");
        waffle = new Waffle(waffleJson.getFloat("posX"), waffleJson.getFloat("posY"));

        JsonValue goalJson = levelData.get("goal");
        goal = new Goal(goalJson.getFloat("posX"), goalJson.getFloat("posY"),
                goalJson.getFloat("width"), goalJson.getFloat("height"));

        inkLimit = levelData.getInt("inkLimit");

        try {
            timeLimit = levelData.getInt("timeLimit");
        } catch (Exception e) {
            timeLimit = 60;
        }

        String[] starData = levelData.getString("starRank").split(";");
        for (int i = 0;i<3;i++){
            starRank.add(Float.parseFloat(starData[i]));
        }

        String shape, type;
        float posX, posY, width, height, radius;
        for (JsonValue obstacleJson : levelData.get("obstacles")) {
            shape = obstacleJson.getString("shape");
            posX = obstacleJson.getFloat("posX");
            posY = obstacleJson.getFloat("posY");
            type = obstacleJson.getString("type");

            if (shape.equals("Round")) {
                radius = obstacleJson.getFloat("radius");
                obstacles.add(new RoundObstacle(posX, posY, radius, type));
            } else {
                width = obstacleJson.getFloat("width");
                height = obstacleJson.getFloat("height");
                if (shape.equals("Square")) {
                    obstacles.add(new SquareObstacle(posX, posY, width, height, type));
                } else if (shape.equals("Triangular")) {
                    obstacles.add(new TriangularObstacle(posX, posY, width, height,type));
                } else {
                    System.out.println("Shape is '"+ shape + "', but must be one of: Square, Triangular or Round");
                }

            }
        }
    }

    public int getInkLimit(){
        return inkLimit;
    }

    public Waffle getWaffle(){
        return waffle;
    }

    public List<Obstacle> getObstacles(){
        return obstacles;
    }

    public Goal getGoal() { return goal; }

    public List<Float> getStarRank(){
        return starRank;
    }

    public String getLevel(){
        return level;
    }

    public static int getNumberOfLevels() {
        JsonReader reader = new JsonReader();
        return reader.parse(Gdx.files.internal("levels.json")).size;
    }

    public int getTimeLimit() {
        return timeLimit;
    }
}