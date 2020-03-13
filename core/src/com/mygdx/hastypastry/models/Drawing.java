package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.HastyPastryGame;

import java.util.ArrayList;

/** The Drawing is an object containing all the lines as drawn by the DrawView
 * @author sigmundhh */
public class Drawing {

    protected Body body;
    protected Sprite sprite;
    protected Shape chainShape;

    private ArrayList<CatmullRomSpline<Vector2>> splines = new ArrayList<CatmullRomSpline<Vector2>>();

    protected Drawing(World world){
        this.sprite = new Sprite();
        // Taken from Obstacle, defines basic things for static bodies
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        this.chainShape = new ChainShape();
    }

    protected void refreshDrawing(ArrayList<ArrayList<Vector2>> lines){
        //1. Unproject to get World coordinates



        //2. Update
        //chainShape.createChain()


        for(ArrayList<Vector2> line : lines){
            // Convert to CatmullRomSpline

        }
    }


}
