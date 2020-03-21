package com.mygdx.hastypastry.interfaces;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public interface WorldObject {
    void addBody(World world);
    Sprite getSprite();
}
