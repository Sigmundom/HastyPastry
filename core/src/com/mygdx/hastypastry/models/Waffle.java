package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Assets;

public class Waffle extends Sprite{
    private Body body;

    private static final float WAFFLE_RADIUS = 1;

    public Waffle(Assets assets, World world, float startX, float startY) {
        super(assets.getManager().get(Assets.waffle));
        setSize(2,2);
        BodyDef def = new BodyDef();
        def.fixedRotation = false;
        def.type = BodyDef.BodyType.DynamicBody;
        System.out.println(startX);
        def.position.set(startX - getWidth()/2, startY - getHeight() / 2);
        this.setOrigin(startX, startY);

        CircleShape shape = new CircleShape();
        shape.setRadius(WAFFLE_RADIUS);

        body = world.createBody(def);

        body.createFixture(shape, 1.0f);
        shape.dispose();
    }

    public void update() {
        this.setPosition(body.getPosition().x, body.getPosition().y);
    }
}
