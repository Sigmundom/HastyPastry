package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.DrawingInputProcessor;
import com.mygdx.hastypastry.HastyPastryGame;

import java.util.ArrayList;

/** The Drawing is an object containing all the lines as drawn by the DrawView
 * @author sigmundhh */
public class Drawing {

    protected Body body;
    protected Sprite sprite;
    protected ChainShape chainShape;
    private DrawingInputProcessor drawingInputProcessor;

    private ArrayList<CatmullRomSpline<Vector3>> splines = new ArrayList<CatmullRomSpline<Vector3>>();

    public Drawing(World world, DrawingInputProcessor drawingInputProcessor){
        this.sprite = new Sprite();
        this.drawingInputProcessor = drawingInputProcessor;
        // Define the body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        //Create a body
        body = world.createBody(bodyDef);

        this.chainShape = new ChainShape();

        //Set shape to body (?)
    }

    protected void setFixture(Shape shape){
        // CodingTrain: Fixuter is like the glue that's attaching the shape to the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = chainShape;
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 1;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    protected void refreshDrawing(ArrayList<ArrayList<Vector3>> lines){



        //1. Update
        // chainShape.createChain();

        for(ArrayList<Vector3> line : lines){
            // Convert to CatmullRomSpline

        }
    }


}
