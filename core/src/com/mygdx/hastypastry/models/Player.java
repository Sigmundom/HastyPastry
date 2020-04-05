package com.mygdx.hastypastry.models;

import com.mygdx.hastypastry.levels.Level;

import java.util.ArrayList;

public class Player {
    private String name;
    private Drawing drawing;
    private Waffle waffle;
    private ArrayList<Object> newLevelTime = new ArrayList<Object>();

//    private InkBar inkBar;

    public Player(String name, Level level) {
        this.name = name;
        this.waffle = new Waffle(level.getWaffle());
        drawing = new Drawing(level.getInkLimit());
    }

    public Player(Level level) {
        this.waffle = new Waffle(level.getWaffle());
        drawing = new Drawing(level.getInkLimit());
    }

    public Waffle getWaffle() {
        return waffle;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public void setNewLevelTime(/*String levelID,*/ float levelTime) {
        //newLevelTime.add(levelID);
        newLevelTime.add(levelTime);
    }

    public float getNewLevelTime() {
        return (float) newLevelTime.get(newLevelTime.size()-1);
    }

    public String getName() {
        return name;
    }
}
