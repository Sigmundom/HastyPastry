package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.hastypastry.singletons.Assets;

public class Wall extends Obstacle {

    public Wall(float posX) {
        super(posX, 16, 2, 32, true);
        sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("deadlysquare"));
    }

    @Override
    protected Shape getShape() {
        Shape shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(2, 16);
        return shape;
    }
}
