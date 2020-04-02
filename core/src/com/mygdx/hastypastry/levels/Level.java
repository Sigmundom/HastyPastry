package com.mygdx.hastypastry.levels;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.FileReader;
import com.mygdx.hastypastry.models.Goal;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.SquareObstacle;
import com.mygdx.hastypastry.models.TriangularObstacle;
import com.mygdx.hastypastry.models.Waffle;
import java.util.ArrayList;

import static com.mygdx.hastypastry.Config.WORLD_HEIGHT;

public class Level {
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private Waffle waffle;
    private Goal goal;
    private FileReader reader = new FileReader();
    private int inkLimit;
    private ArrayList<Float> starRank = new ArrayList<>();
    private float levelTime;
    private String levelNumber;

    //Read form file with FileReader class and creates all objects and values form the file
    public Level(String level){
        ArrayList<String> levelData = new ArrayList<>();
        ArrayList<String> resultData = new ArrayList<>();
        levelNumber = level;
        levelData = reader.getInternalFileData("levels.txt");
        resultData = reader.getLocalFileData("results.txt");
        int start;
        String[] waffleData;
        String[] goalData;
        String[] inkData;
        String[] obstacleData;
        String[] starData;
        String timeData;
        int i = 1;

        //Get data from result file
        for (String result : resultData){
            if (result.equals(levelNumber)){
                timeData = resultData.get(resultData.indexOf(result)+1);
                levelTime = Float.parseFloat(timeData);
            }
            break;
        }

        //Get data from levels file
        for(String line : levelData) {

            if (line.equals(levelNumber)) {

                start = levelData.indexOf(line);
                line = levelData.get(start+1);
                waffleData = line.split(" ");
                waffle = new Waffle(StringToInt(waffleData[1]), StringToInt(waffleData[2]));
                line = levelData.get(start+2);
                goalData = line.split(" ");
                goal = new Goal(StringToInt(goalData[1]), StringToInt(goalData[2]), StringToInt(goalData[3]), StringToInt(goalData[4]));
                line = levelData.get(start+3);
                inkData = line.split(" ");
                inkLimit = StringToInt(inkData[1]);
                line = levelData.get(start+4);
                starData = line.split(" ");
                for (int s = 1;s<starData.length;s++){
                    starRank.add(Float.parseFloat(starData[s]));
                }
                line = levelData.get(start+5);
                while (!line.contains("#")) {
                    obstacleData = line.split(" ");
                    if(obstacleData[1].contains("Round")) {
                        obstacles.add(new RoundObstacle(StringToInt(obstacleData[2]), StringToInt(obstacleData[3]), StringToInt(obstacleData[4]), StringToBoolean(obstacleData[5])));
                    }
                    else if (obstacleData[1].contains("Square")){
                        obstacles.add(new SquareObstacle(StringToInt(obstacleData[2]), StringToInt(obstacleData[3]), StringToInt(obstacleData[4]), StringToInt(obstacleData[5]) , StringToBoolean(obstacleData[6])));
                    }
                    else if (obstacleData[1].contains("Triangular")){
                        obstacles.add(new TriangularObstacle(StringToInt(obstacleData[2]), StringToInt(obstacleData[3]), StringToInt(obstacleData[4]), StringToInt(obstacleData[5]) , StringToBoolean(obstacleData[6])));
                    }
                    line = levelData.get(start+5+i);
                    i++;
                }
                break;
            }
        }
    }

    private int StringToInt(String input){
        if(input.contains("middleWidth")){
            return Config.WORLD_WIDTH/2;
        }
        else if (input.contains("middleHeight")){
            return WORLD_HEIGHT/2;
        }
        else {

            return Integer.parseInt(input);
        }
    }

    private boolean StringToBoolean(String input){
        if (input.contains("false")){
            return false;
        }
        else {

            return true;
        }
    }



    public int getInkLimit(){
        return inkLimit;
    }

    public Waffle getWaffle(){
        return waffle;
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }

    public Goal getGoal() { return goal; }

    public ArrayList<Float> getStarRank(){
        return starRank;
    }

    public float getLevelTime(){
        return levelTime;
    }

    public String getLevelNumber(){
        return levelNumber;
    }
}

