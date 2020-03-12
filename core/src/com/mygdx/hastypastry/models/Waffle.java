package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;

public class Waffle extends Sprite{
    private Body body;

    private static final float WAFFLE_RADIUS = 25;

    public Waffle(World world, float startX, float startY) {
        super(new Texture("Waffle.png"));

        BodyDef def = new BodyDef();
        def.fixedRotation = false;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(startX / Config.PIXEL_PER_METER, startY / Config.PIXEL_PER_METER);

        CircleShape shape = new CircleShape();
        shape.setRadius(WAFFLE_RADIUS / Config.PIXEL_PER_METER);

        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.density = 1.0f;
        fixturedef.filter.categoryBits = 1; //What it is
        fixturedef.filter.maskBits = 2;     //Collides with
        //fixturedef.friction = 0;

        body = world.createBody(def);
        body.createFixture(fixturedef);

        shape.dispose();
    }

    public Body getBody() {
        return body;
    }

    public void update() { }

    public void dispose() {
        this.getTexture().dispose();
    }
}
