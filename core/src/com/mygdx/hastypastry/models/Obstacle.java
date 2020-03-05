package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public abstract class Obstacle {

    protected Vector2 pos;
    protected Texture texture;
    protected Boolean isDeadly;
    protected List<Vector2> boundary;

    protected abstract void setBoundary();

    public Vector2 getPos(){ return pos; }
    public Texture getTexture(){ return texture; }
    public Boolean getIsDeadly(){ return isDeadly; }
    public List<Vector2> getBoundary(){ return boundary; }
}
