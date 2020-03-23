package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.hastypastry.Assets;

public class RoundObstacle extends Obstacle {
    private float radius;

    public RoundObstacle(float posX, float posY, float radius, boolean isDeadly){
        super(posX, posY, 2*radius, 2*radius, isDeadly);
        this.radius = radius;
        if (isDeadly){
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("deadlycircle"));
        } else {
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("circle"));
        }
    }

    protected Shape getShape() {
        Shape shape = new CircleShape();
        shape.setRadius(radius);
        return shape;
    }
}
