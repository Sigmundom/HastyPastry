package com.mygdx.hastypastry.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.hastypastry.models.Goal;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.SquareObstacle;
import com.mygdx.hastypastry.models.TriangularObstacle;
import com.mygdx.hastypastry.models.Waffle;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Obstacle> obstacles = new ArrayList<>();
    private Waffle waffle;
    private Goal goal;
    private int inkLimit;
    private List<Float> starRank = new ArrayList<>();
    private String level;
    private JsonValue levelData;

    public Level(String level){
        this.level = level;
        int index = Integer.parseInt(String.valueOf(level.charAt(level.length()-1))) - 1;

        JsonReader jsonReader = new JsonReader();
        levelData = jsonReader.parse(Gdx.files.internal("levels.json")).get(index);

        JsonValue waffleJson = levelData.get("waffle");
        waffle = new Waffle(waffleJson.getFloat("posX"), waffleJson.getFloat("posY"));

        JsonValue goalJson = levelData.get("goal");
        goal = new Goal(goalJson.getFloat("posX"), goalJson.getFloat("posY"),
                goalJson.getFloat("width"), goalJson.getFloat("height"));

        inkLimit = levelData.getInt("inkLimit");

        String[] starData = levelData.getString("starRank").split(";");
        for (int i = 0;i<3;i++){
            starRank.add(Float.parseFloat(starData[i]));
        }

        String shape;
        float posX, posY, width, height, radius;
        boolean isDeadly;
        for (JsonValue obstacleJson : levelData.get("obstacles")) {
            shape = obstacleJson.getString("shape");
            posX = obstacleJson.getFloat("posX");
            posY = obstacleJson.getFloat("posY");
            isDeadly = obstacleJson.getBoolean("isDeadly");

            if (shape.equals("Round")) {
                radius = obstacleJson.getFloat("radius");
                obstacles.add(new RoundObstacle(posX, posY, radius, isDeadly));
            } else {
                width = obstacleJson.getFloat("width");
                height = obstacleJson.getFloat("height");
                if (shape.equals("Square")) {
                    obstacles.add(new SquareObstacle(posX, posY, width, height, isDeadly));
                } else if (shape.equals("Triangular")) {
                    obstacles.add(new TriangularObstacle(posX, posY, width, height,isDeadly));
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
}