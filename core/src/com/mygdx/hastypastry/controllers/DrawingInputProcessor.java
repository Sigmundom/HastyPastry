package com.mygdx.hastypastry.controllers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.hastypastry.models.Drawing;
import java.util.ArrayList;

/** The DrawingInputProcessor will handle the touch input and make a list of lines.
 * A line consists of semi-equidistant points.
 * @author sigmundhh */

public class DrawingInputProcessor implements InputProcessor {
    private Camera camera;
    private Drawing drawing;
    private Vector3 lastPoint;
    private boolean isDrawing = false;

    public DrawingInputProcessor(Camera camera, Drawing drawing){
        this.camera = camera;
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
        drawing.addLine(new ArrayList<Vector3>());
        Vector3 point = camera.unproject(new Vector3(screenX, screenY, 0));
        point.z = 0.5f; // z=0.5 means first point use 0.5 unit of ink
        lastPoint = point;
        drawing.addPoint(point);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDrawing = false;
        // End line here
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        float inkLeft = drawing.getInkbar().getInkLeft();

        if (isDrawing && inkLeft > 0.1f) {
            // New point world coordinates
            Vector3 newPoint = camera.unproject(new Vector3(screenX, screenY, 0));

            // Setting the z-component equal to last point's z-component to get correct distance calculations.
            newPoint.z = lastPoint.z;

            // Vector from last point to new point.
            Vector3 vector = newPoint.cpy().sub(lastPoint);

            // One unit of ink equals one length unit.
            float inkRequired = vector.len();

            if (inkRequired < 0.1f) {
                // Prevent to close points
                return false;
            }

            if (inkRequired > inkLeft) {
                // Limits the line if not enough ink
                vector.setLength(inkLeft);
                newPoint = lastPoint.add(vector);
                newPoint.z = inkLeft;
            } else {
                // The z-component represents the ink used for the specific line segment
                newPoint.z = inkRequired;
            }

            lastPoint = newPoint;
            drawing.addPoint(newPoint);
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
