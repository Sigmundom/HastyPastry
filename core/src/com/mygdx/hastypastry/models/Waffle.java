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
    private float lowestPoint;
    private float lowestPointOnLastTurn;
    private String horizontalDirection = "none";
    private boolean isDead = false;

    public Waffle(float posX, float posY) {
        this.lowestPoint = posY;
        this.lowestPointOnLastTurn = this.lowestPoint;
        sprite = new Sprite();
        sprite.setPosition(posX - RADIUS, posY - RADIUS);
        sprite.setOrigin(RADIUS, RADIUS); //Sets the origin for rotation
        sprite.setSize(2*RADIUS, 2*RADIUS);
        sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("waffle"));
    }

    // Makes a deep copy of the waffle in Level.
    public Waffle(Waffle waffle, boolean isPlayer) {
        sprite = new Sprite(waffle.getSprite());
        this.lowestPoint = sprite.getY();
        this.lowestPointOnLastTurn = this.lowestPoint;
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
        if (!isDead) {
            sprite.setPosition(body.getPosition().x - RADIUS, body.getPosition().y - RADIUS);
            sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        }
    }

    protected boolean WaffleHasStopped() {
        if (isDead) {
            // Don't bother if waffle already is registered dead.
            return false;
        }
        if (body.getLinearVelocity().isZero()) {
            // GameOver: Full stop
            return true;
        }

        if (sprite.getY() < lowestPoint) {
            lowestPoint = sprite.getY();
        }
        // Check if direction has changed since last update.
        String newHorizontalDirection;
        float xVelocity = body.getLinearVelocity().x;
        if (xVelocity > 0) {
            newHorizontalDirection = "right";
        } else if (xVelocity < 0) {
            newHorizontalDirection = "left";
        } else {
            newHorizontalDirection = "none";
        }
        if (!horizontalDirection.equals(newHorizontalDirection)) {
            // Waffle has turned since last update
            if (lowestPointOnLastTurn - lowestPoint < 0.001f) {
                // Game Over: Not been any lower since last turn. (0.001f margin)
                return true;
            } else {
                // Register turn and updating lowestPointOnLastTurn
                horizontalDirection = newHorizontalDirection;
                lowestPointOnLastTurn = lowestPoint;
            }
        }
        return false;
    }

    public void setIsDead() {
        this.isDead = true;
        body.setType(BodyDef.BodyType.StaticBody);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isDead() {
        return isDead;
    }
}
