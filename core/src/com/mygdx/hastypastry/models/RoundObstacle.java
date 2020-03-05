package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class RoundObstacle extends Obstacle {

    public RoundObstacle(Vector2 position, Boolean isDeadly){
        super.pos = position;
        super.isDeadly = isDeadly;
        setBoundary();
        if (isDeadly){
            super.texture = new Texture("deadlyCircle.png");
        } else {
            super.texture = new Texture("circle.png");
        }
    }

    @Override
    protected void setBoundary() {

    }
}
