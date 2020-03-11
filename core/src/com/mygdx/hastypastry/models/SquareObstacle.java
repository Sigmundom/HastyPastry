package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class SquareObstacle extends Obstacle{

    public SquareObstacle(World world, Vector2 position, Boolean isDeadly){
        super.pos = position;
        super.isDeadly = isDeadly;
        setBoundary();
        if (isDeadly){
            super.texture = new Texture("deadlySquare.png");
        } else {
            super.texture = new Texture("square.png");
        }
        super.body = defineBody(world, position);
        super.body.createFixture(setBoundary(), 1.0f);
    }

    @Override
    protected Shape setBoundary() {
        PolygonShape shape = new PolygonShape();
        float[] vertices = new float[8];
        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 50;
        vertices[3] = 0;
        vertices[4] = 50;
        vertices[5] = 50;
        vertices[6] = 0;
        vertices[7] = 50;
        shape.set(vertices);
        return shape;
    }
}
