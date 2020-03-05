package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class Waffle {
    private Body wBody;

    private static final float WAFFLE_RADIUS = 20;

    public Waffle(World world, float start_X, float start_Y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(start_X, start_Y);
        def.fixedRotation = false;
        wBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(WAFFLE_RADIUS);

        wBody.createFixture(shape, 1.0f);

        shape.dispose();
    }

    /*public void update() {
        world.step(1/60f, 6, 2);
    }*/

    public Body getWaffle() {
        return wBody;
    }

}
