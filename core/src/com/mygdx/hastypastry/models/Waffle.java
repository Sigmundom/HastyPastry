package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Assets;

public class Waffle {
    private final float RADIUS = 1;
    private Body body;
    private Sprite sprite;

    public Waffle(Assets assets, World world, float posX, float posY) {
        sprite = new Sprite();
        sprite.setPosition(posX - RADIUS, posY - RADIUS);
        sprite.setOrigin(RADIUS, RADIUS); //Sets the origin for rotation
        sprite.setSize(2*RADIUS, 2*RADIUS);
        sprite.setRegion(assets.getManager().get(Assets.gameTextures).findRegion("waffle"));

        BodyDef def = new BodyDef();
        def.fixedRotation = false;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(posX, posY); //Set body position

        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.density = 1.0f;
        fixturedef.filter.categoryBits = 1; //What it is
        fixturedef.filter.maskBits = 2;     //Collides with

        body = world.createBody(def);
        body.setUserData(true);
        body.createFixture(fixturedef);

        body.createFixture(shape, 1.0f);
        shape.dispose();
    }

    public void update() {
        sprite.setPosition(body.getPosition().x - RADIUS, body.getPosition().y - RADIUS);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }
    public Sprite getSprite() {
        return sprite;
    }
}
