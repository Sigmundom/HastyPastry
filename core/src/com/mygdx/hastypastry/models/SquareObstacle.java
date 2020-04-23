package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.hastypastry.singletons.Assets;

public class SquareObstacle extends Obstacle{
    private float width, height;

    // Fixture and shape is defined here, while the rest is common for all obstacles and defined in superclass.
    public SquareObstacle(float posX, float posY, float width, float height, String type){
        super(posX, posY, width, height, type);
        this.width = width;
        this.height = height;

        if (this.isDeadly()){
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("deadlysquare"));
        } else if (this.isBouncing()) {
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("bouncingsquare"));
        } else  {
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("square"));
        }
    }

    protected Shape getShape(){
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(width/2, height/2);
        return shape;
    }
}
