package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;

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

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = setBoundary();
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 1;

        super.body.createFixture(fixtureDef);
        System.out.println(body.getPosition().x + ", " + body.getPosition().y);
    }

    @Override
    protected Shape setBoundary() {
        CircleShape shape = new CircleShape();
        shape.setRadius(25 / Config.PIXEL_PER_METER);
        return shape;
    }
}
