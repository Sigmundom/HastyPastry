package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/** The Drawing is an object containing all the lines as drawed by the DrawView
 * @author sigmundhh */
public class Drawing {

    private ArrayList<CatmullRomSpline<Vector2>> splines = new ArrayList<CatmullRomSpline<Vector2>>();

    public Drawing(ArrayList<ArrayList<Vector2>> lines){
        for(ArrayList<Vector2> line : lines){
            // Convert to CatmullRomSpline
        }

    }

}
