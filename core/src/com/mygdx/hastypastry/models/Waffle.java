package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Waffle {
    private Body wBody;
    private Sprite sprite;
    private CircleShape shape;
    private BodyDef def;

    private static final float WAFFLE_RADIUS = 7;

    public Waffle(World world, float start_X, float start_Y) {
        def = new BodyDef();
        def.fixedRotation = false;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(start_X, start_Y);

        shape = new CircleShape();
        shape.setRadius(WAFFLE_RADIUS);

        sprite = new Sprite(new Texture("Waffle.png"));

        wBody = world.createBody(def);
        wBody.setUserData(sprite);

        //wBody.createFixture(shape, 1.0f);
    }

    public void update() { }

    public void render(SpriteBatch sb) { update(); }

    public Body getWaffle() { return wBody; }

    public Texture getWaffleTexture() { return sprite.getTexture(); }

    public void dispose() {
        sprite.getTexture().dispose();
        shape.dispose();
    }
}
