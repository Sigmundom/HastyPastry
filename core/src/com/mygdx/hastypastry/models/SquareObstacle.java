package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.hastypastry.Assets;

public class SquareObstacle extends Obstacle{
    private float width, height;

    // Fixture and shape is defined here, while the rest is common for all obstacles and defined in superclass.
    public SquareObstacle(float posX, float posY, float width, float height, boolean isDeadly){
        super(posX, posY, width, height, isDeadly);
        this.width = width;
        this.height = height;

        if (isDeadly){
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("deadlysquare"));
        } else {
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("square"));
        }
    }

    protected Shape getShape(){
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(width/2, height/2);
        return shape;
    }
}
