package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class TriangularObstacle extends Obstacle{

    public TriangularObstacle(World world, Vector2 position, Boolean isDeadly){
        super.pos = position;
        super.isDeadly = isDeadly;
        setBoundary();
        if (isDeadly){
            super.texture = new Texture("deadlyTriangle.png");
        } else {
            super.texture = new Texture("triangle.png");
        }
        super.body = defineBody(world, position);
        super.body.createFixture(setBoundary(), 1.0f);
    }

    @Override
    protected Shape setBoundary() {
        PolygonShape shape = new PolygonShape();
        float[] vertices = new float[6];
        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 40;
        vertices[3] = 0;
        vertices[4] = 20;
        vertices[5] = 40;
        shape.set(vertices);
        return shape;
    }
}
