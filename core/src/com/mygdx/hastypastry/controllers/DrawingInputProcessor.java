package com.mygdx.hastypastry.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.hastypastry.models.Drawing;
import java.util.ArrayList;

/** The DrawingInputProcessor will handle the touch input and make a list of lines.
 * A line consists of semi-equidistant points.
 * @author sigmundhh */

public class DrawingInputProcessor implements InputProcessor {

    private ArrayList<ArrayList<Vector3>> lines = new ArrayList<ArrayList<Vector3>>();
    private static float minDistSqrd = 0.5f;
    private Camera cam;
    private Drawing drawing;
    private Vector3 lastPoint;
    private boolean isDrawing = false;

    public DrawingInputProcessor(Camera cam, Drawing drawing){
        this.cam = cam;
        this.drawing = drawing;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        isDrawing = true;
        // Add new line here
        ArrayList<Vector2> line = new ArrayList<>();
        Vector3 v3 = cam.unproject(new Vector3(screenX, screenY, 0));
        lastPoint = v3;
        line.add(new Vector2(v3.x, v3.y));
        drawing.addLine(line);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDrawing = false;
        // End line here
        System.out.println("touchUp!");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDrawing) {
            Vector3 v3 = cam.unproject(new Vector3(screenX, screenY, 0));
            Vector2 newPoint = new Vector2(v3.x, v3.y);

            //determine squared distance between input and last point
            float lenSq = v3.sub(lastPoint).len2();

            //the minimum distance between input points, squared
            if (lenSq >= minDistSqrd) {
                drawing.addPoint(newPoint);
                lastPoint = new Vector3(newPoint, 0);
            }
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
