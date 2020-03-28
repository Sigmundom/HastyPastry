package com.mygdx.hastypastry.levels;

import com.mygdx.hastypastry.models.Goal;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.models.Waffle;

import java.util.ArrayList;

public abstract class Level {
    protected ArrayList<Obstacle> obstacles;
    protected Waffle waffle;
    protected Goal goal;
    protected ArrayList<Float> starValues;

    protected Level(){
        obstacles = new ArrayList<>();
        starValues = new ArrayList<>();
    }

    public Waffle getWaffle(){
        return waffle;
    }

    public ArrayList<Obstacle> getObstacles(){
        return obstacles;
    }

    public Goal getGoal() { return goal; }

    public ArrayList<Float> getStarValues() { return starValues; }
}
