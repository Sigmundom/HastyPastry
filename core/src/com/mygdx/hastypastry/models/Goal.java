package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Assets;

public class Goal {

    private Body body;
    private Sprite sprite;

    public Goal(Assets assets, World world, float posX, float posY){

        Sprite sprite = new Sprite();
        sprite.setSize(50,50);
        sprite.setPosition(posX - 25, posY - 25);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        sprite.setRegion(assets.getManager().get(Assets.gameTextures).findRegion("Flag"));

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25, 25);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 1;

        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
