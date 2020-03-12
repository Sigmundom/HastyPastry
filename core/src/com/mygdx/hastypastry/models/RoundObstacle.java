package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Assets;

public class RoundObstacle extends Obstacle {

    public RoundObstacle(Assets assets, World world, float posX, float posY, float radius, boolean isDeadly){
        super(world, posX, posY, 2*radius, 2*radius);
        this.isDeadly = isDeadly;
        if (isDeadly){
            sprite.setRegion(assets.getManager().get(Assets.gameTextures).findRegion("deadlyCircle"));
        } else {
            sprite.setRegion(assets.getManager().get(Assets.gameTextures).findRegion("circle"));
        }
        Shape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 1;

        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
