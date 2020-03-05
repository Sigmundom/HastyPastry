package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TriangularObstacle extends Obstacle{

    public TriangularObstacle(Vector2 position, Boolean isDeadly){
        super.pos = position;
        super.isDeadly = isDeadly;
        setBoundary();
        if (isDeadly){
            super.texture = new Texture("deadlyTriangle.png");
        } else {
            super.texture = new Texture("triangle.png");
        }
    }

    @Override
    protected void setBoundary() {

    }
}
