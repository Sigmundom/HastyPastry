package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.hastypastry.Assets;

public class SquareObstacle extends Obstacle{

    // Fixture and shape is defined here, while the rest is common for all obstacles and defined in superclass.
    public SquareObstacle(Assets assets, World world, float posX, float posY, float width, float height, boolean isDeadly){
        super(world, posX, posY, width, height, isDeadly);

        if (isDeadly){
            sprite.setRegion(assets.getManager().get(Assets.gameTextures).findRegion("deadlysquare"));
        } else {
            sprite.setRegion(assets.getManager().get(Assets.gameTextures).findRegion("square"));
        }

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);

        setFixture(shape);
    }
}
