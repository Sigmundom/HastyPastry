package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

public abstract class Obstacle {

    protected Body body;
    protected Vector2 pos;
    protected Texture texture;
    protected Boolean isDeadly;
    protected List<Vector2> boundary;

    protected abstract Shape setBoundary();

    protected Body defineBody(World world, Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return world.createBody(bodyDef);
    }


    public Body getBody(){ return body; }
    public Vector2 getPos(){ return pos; }
    public Texture getTexture(){ return texture; }
    public Boolean getIsDeadly(){ return isDeadly; }
    public List<Vector2> getBoundary(){ return boundary; }

    public void dispose() {
        texture.dispose();
    }
}
