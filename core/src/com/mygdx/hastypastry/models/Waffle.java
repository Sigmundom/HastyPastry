package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.interfaces.WorldObject;

public class Waffle implements WorldObject {
    private final float RADIUS = 1;
    private Body body;
    private Sprite sprite;
    private boolean isPlayer;

    public Waffle(float posX, float posY) {
        sprite = new Sprite();
        sprite.setPosition(posX - RADIUS, posY - RADIUS);
        sprite.setOrigin(RADIUS, RADIUS); //Sets the origin for rotation
        sprite.setSize(2*RADIUS, 2*RADIUS);
        sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("waffle"));
    }

    // Makes a deep copy of the waffle in Level.
    public Waffle(Waffle waffle, boolean isPlayer) {
        sprite = new Sprite(waffle.getSprite());
        if(!isPlayer) {
            sprite.setAlpha(0.3f);
        }
        this.isPlayer = isPlayer;
    }

    public void addBody(World world) {
        BodyDef def = new BodyDef();
        def.fixedRotation = false;
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(sprite.getX()+RADIUS, sprite.getY()+RADIUS); //Set body position

        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);

        body = world.createBody(def);
        FixtureDef fixturedef = new FixtureDef();
        fixturedef.shape = shape;
        fixturedef.density = 1.0f;

        fixturedef.filter.categoryBits = 1; //What it is
        fixturedef.filter.maskBits = (short) (isPlayer ? 2 | 4 : 2 | 8);     //Collides with

        body.setUserData(isPlayer ? "playerWaffle" : "opponentWaffle");
        body.createFixture(fixturedef);

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
