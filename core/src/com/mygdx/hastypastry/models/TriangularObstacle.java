package com.mygdx.hastypastry.models;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.hastypastry.singletons.Assets;

public class TriangularObstacle extends Obstacle{
    private float width, height;

    public TriangularObstacle(float posX, float posY, float width, float height, String type){
        super(posX, posY, width, height, type);
        this.width = width;
        this.height = height;

        if (this.isDeadly()){
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("deadlytriangle"));
        } else if (this.isBouncing()) {
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("bouncingtriangle"));
        } else {
            sprite.setRegion(Assets.instance.getManager().get(Assets.gameTextures).findRegion("triangle"));
        }
    }

    protected Shape getShape() {
        Shape shape = new PolygonShape();
        float[] vertices = {-width/2, -height/2, width/2, -height/2, 0, height/2};
        ((PolygonShape) shape).set(vertices);
        return shape;
    }
}
