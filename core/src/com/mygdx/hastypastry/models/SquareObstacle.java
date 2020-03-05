package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class SquareObstacle extends Obstacle{

    public SquareObstacle(Vector2 position, Boolean isDeadly){
        super.pos = position;
        super.isDeadly = isDeadly;
        setBoundary();
        if (isDeadly){
            super.texture = new Texture("deadlySquare.png");
        } else {
            super.texture = new Texture("square.png");
        }
    }

    @Override
    protected void setBoundary() {

    }
}
