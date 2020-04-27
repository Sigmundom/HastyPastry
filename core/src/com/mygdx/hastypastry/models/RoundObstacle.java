package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.hastypastry.singletons.Assets;

public class RoundObstacle extends Obstacle {
    private float radius;

    public RoundObstacle(float posX, float posY, float radius, String type){
        super(posX, posY, 2*radius, 2*radius, type);
        this.radius = radius;
        if (this.isDeadly()){
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("deadlycircle"));
        } else if (this.isBouncing()) {
            sprite.setRegion((Assets.instance.getManager().get(Assets.gameTextures).findRegion("bouncingcircle")));
        } else {
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("circle"));
        }
    }

    protected Shape generateShape() {
        Shape shape = new CircleShape();
        shape.setRadius(radius);
        return shape;
    }
}
