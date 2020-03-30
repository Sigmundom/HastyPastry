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
    private ArrayList<String> levelData = new ArrayList<>();
    private int inkLimit;


    public Level(String level){
        levelData = reader.getFileData("levels.txt");
        int start;
        String[] waffleData;
        String[] goalData;
        String[] inkData;
        String[] obstacleData;
        int i = 1;

        for(String line : levelData) {
            if (line.contains("Level 1")) {
                System.out.println(line);
                start = levelData.indexOf(line);
                line = levelData.get(start+1);
                waffleData = line.split(" ");
                waffle = new Waffle(StringConverter(waffleData[1]), StringConverter(waffleData[2]));
                line = levelData.get(start+2);
                goalData = line.split(" ");
                goal = new Goal(StringConverter(goalData[1]), StringConverter(goalData[2]), StringConverter(goalData[3]), StringConverter(goalData[4]));
                line = levelData.get(start+3);
                inkData = line.split(" ");
                inkLimit = StringConverter(inkData[1]);
                line = levelData.get(start+5);
                while (!line.contains("#")) {
                    obstacleData = line.split(" ");
                    if(obstacleData[1].contains("Round")) {
                        obstacles.add(new RoundObstacle(StringConverter(obstacleData[2]), StringConverter(obstacleData[3]),StringConverter(obstacleData[4]), BooleanConverter(obstacleData[5])));
                    }
                    else if (obstacleData[1].contains("Square")){
                        obstacles.add(new SquareObstacle(StringConverter(obstacleData[2]), StringConverter(obstacleData[3]),StringConverter(obstacleData[4]),StringConverter(obstacleData[5]) ,BooleanConverter(obstacleData[6])));
                    }
                    else if (obstacleData[1].contains("Triangular")){
                        obstacles.add(new TriangularObstacle(StringConverter(obstacleData[2]), StringConverter(obstacleData[3]),StringConverter(obstacleData[4]),StringConverter(obstacleData[5]) ,BooleanConverter(obstacleData[6])));
                    }
                    line = levelData.get(start+5+i);
                    i++;
                }
                break;
            }
        }
    }

    private int StringConverter(String input){
        System.out.println(input);
        if(input.contains("middleWidth")){
            return Config.WORLD_WIDTH/2;
        }
        else if (input.contains("middleHeight")){
            return WORLD_HEIGHT/2;
        }
        else {
            if(input.contains("\r")){
                input = input.substring(0,input.length()-1);
            }

            return Integer.parseInt(input);
        }
    }

    private boolean BooleanConverter(String input){
        System.out.println(input);
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
}
