package com.mygdx.hastypastry.models;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private Drawing drawing;
    private Waffle waffle;
    private List<Float> newLevelTime = new ArrayList<>();
    private boolean isReady = false;

//    private InkBar inkBar;

    public Player(String name, Level level, boolean isPlayer) {
        this.name = name;
        this.waffle = new Waffle(level.getWaffle(), isPlayer);
        drawing = new Drawing(level.getInkLimit(), isPlayer);
    }

    public Player(Level level) {
        this.waffle = new Waffle(level.getWaffle(), true);
        drawing = new Drawing(level.getInkLimit(), true);
    }

    public Waffle getWaffle() {
        return waffle;
    }

    public Drawing getDrawing() {
        return drawing;
    }

    public void setNewLevelTime(float levelTime) {
        newLevelTime.add(levelTime);
    }

    public float getNewLevelTime() {
        return (float) newLevelTime.get(newLevelTime.size()-1);
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady() {
        isReady = true;
    }
}
