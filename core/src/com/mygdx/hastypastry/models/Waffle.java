package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;

public class Waffle extends Sprite{
    private Body body;

    private static final float WAFFLE_RADIUS = 7;

    public Waffle(World world, float startX, float startY) {
        super(new Texture("Waffle.png"));
        BodyDef def = new BodyDef();
        def.fixedRotation = false;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(startX / Config.PIXEL_PER_METER, startY / Config.PIXEL_PER_METER);

        CircleShape shape = new CircleShape();
        shape.setRadius(WAFFLE_RADIUS);

        body = world.createBody(def);

        body.createFixture(shape, 1.0f).setUserData(this);
        shape.dispose();
    }

    public void update() { }

    public void draw(SpriteBatch sb) {
        sb.draw(this.getTexture(), body.getPosition().x * Config.PIXEL_PER_METER - (this.getTexture().getWidth() / 2f),
                body.getPosition().y * Config.PIXEL_PER_METER - (this.getTexture().getHeight() / 2f));
    }


    public void dispose() {
        this.getTexture().dispose();
    }
}
