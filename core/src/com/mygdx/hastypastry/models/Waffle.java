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
    private Texture texture;
    private Vector2 position;
    private Sprite sprite;
    private float rotation;

    private static final float WAFFLE_RADIUS = 7;

    public Waffle(World world, float start_X, float start_Y) {
        BodyDef def = new BodyDef();
        def.fixedRotation = false;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(start_X, start_Y);
        //position = new Vector2(start_X, start_Y);

        CircleShape shape = new CircleShape();
        shape.setRadius(WAFFLE_RADIUS);

        sprite = new Sprite(new Texture("Waffle.png"));

        wBody = world.createBody(def);
        wBody.setUserData(sprite);
        wBody.createFixture(shape, 1.0f);

        shape.dispose();
    }

    public void update() {
        rotation = (float)Math.toDegrees(wBody.getAngle());
        //sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
    }

    public void render(SpriteBatch sb) {
        update();
        sprite.draw(sb);
        ((Sprite) wBody.getUserData()).draw(sb);
    }

    public Body getWaffle() {
        return wBody;
    }

    public Texture getWaffleTexture() {
        return sprite.getTexture();
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }
}
