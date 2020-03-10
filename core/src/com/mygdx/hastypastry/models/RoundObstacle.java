package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class RoundObstacle extends Obstacle {

    public RoundObstacle(World world, Vector2 position, Boolean isDeadly){
        super.pos = position;
        super.isDeadly = isDeadly;
        setBoundary();
        if (isDeadly){
            super.texture = new Texture("deadlyCircle.png");
        } else {
            super.texture = new Texture("circle.png");
        }
        super.body = defineBody(world, position);
        super.body.createFixture(setBoundary(), 1.0f);
    }

    @Override
    protected Shape setBoundary() {
        CircleShape shape = new CircleShape();
        shape.setRadius(50);
        return shape;
    }
}
