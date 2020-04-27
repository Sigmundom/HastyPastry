package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.singletons.Assets;

public class Goal implements WorldObject {

    private float posX, posY, width, height;
    private Sprite sprite;

    public Goal(float posX, float posY, float width, float height){

        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        sprite = new Sprite();
        sprite.setSize(width,height);
        sprite.setPosition(posX - width/2, posY - height/2);
        sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("flag"));
    }

    public void addBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        body.setUserData("goal");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2, height/2);
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
