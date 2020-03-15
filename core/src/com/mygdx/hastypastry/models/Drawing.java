package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.Stack;

/** The Drawing is an object containing all the lines as drawn by the DrawView
 * @author sigmundhh */
public class Drawing {

    private Stack<ArrayList<Vector2>> lines = new Stack<>();

    public void addLine(ArrayList<Vector2> line) {
        lines.push(line);
    }

    public void addPoint(Vector2 point) {
        lines.peek().add(point);
    }


    public Stack<ArrayList<Vector2>> getLines() {
        return lines;
    }




}
