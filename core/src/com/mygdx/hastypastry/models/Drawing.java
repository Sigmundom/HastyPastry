package com.mygdx.hastypastry.models;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;
import java.util.Stack;

/** The Drawing is an object containing all the lines as drawn by the DrawView
 * @author sigmundhh */
public class Drawing {

    ShapeRenderer shapeRenderer;
    private Viewport viewport;
    private Stack<ArrayList<Vector2>> lines = new Stack<>();


    public Drawing(Viewport viewport){
        this.viewport = viewport;
        shapeRenderer = new ShapeRenderer();
    }

    public void addLine(ArrayList<Vector2> line) {
        lines.push(line);
    }

    public void addPoint(Vector2 point) {
        lines.peek().add(point);
    }


    public void draw() {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        for (ArrayList<Vector2> line: lines) {
            for(int i = 0; i < line.size()-1; ++i) {
                shapeRenderer.rectLine(line.get(i), line.get(i+1), 0.5f);
            }
        }
        shapeRenderer.end();
    }




}
