package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.interfaces.WorldObject;

public abstract class Obstacle implements WorldObject {

    private Body body;
    private float posX;
    private float posY;
    protected Sprite sprite;
    protected String type;

    protected Obstacle(float posX, float posY, float width, float height, String type) {
        this.posX = posX;
        this.posY = posY;
        this.type = type;
        sprite = new Sprite();
        sprite.setSize(width, height);
        sprite.setPosition(posX - width/2, posY - height/2);

    }

    public boolean isDeadly() {return type.equals("deadly");}

    public boolean isBouncing() {return type.equals("bouncing");}

    protected abstract Shape getShape();

    public void addBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        if (isDeadly()){
            body.setUserData("deadly");
        } else {
            body.setUserData("safe");
        }

        Shape shape = getShape();
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 1;
        if (isBouncing()) {
            fixtureDef.restitution = 1.5f;
        }


        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }
}
