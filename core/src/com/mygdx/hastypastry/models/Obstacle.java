package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.interfaces.WorldObject;

public abstract class Obstacle implements WorldObject {

    protected Body body;
    protected Sprite sprite;
    protected float posX;
    protected float posY;
    protected boolean isDeadly;
    protected Shape shape;

    protected Obstacle(float posX, float posY, float width, float height, boolean isDeadly) {
        this.posX = posX;
        this.posY = posY;
        this.isDeadly = isDeadly;
        sprite = new Sprite();
        sprite.setSize(width, height);
        sprite.setPosition(posX - width/2, posY - height/2);

    }

    public void addBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.setUserData(isDeadly);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 1;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }
}
