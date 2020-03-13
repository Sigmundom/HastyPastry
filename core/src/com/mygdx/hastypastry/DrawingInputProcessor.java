package com.mygdx.hastypastry;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.lang.reflect.Array;
import java.util.ArrayList;

/** The DrawingInputProcessor will handle the touch input and make a list of lines.
 * A line consists of semi-equidistant points.
 * @author sigmundhh */

public class DrawingInputProcessor implements InputProcessor {

    private ArrayList<ArrayList<Vector3>> lines = new ArrayList<ArrayList<Vector3>>();
    private static int minDistSqrd = 2;
    private Camera cam;

    public DrawingInputProcessor(Camera cam){
        this.cam = cam;
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
        // Add new line here
        System.out.println("Start! " + screenX + ", "  + screenY);
        lines.add(new ArrayList<Vector3>());
        ArrayList<Vector3> currentLine = lines.get(lines.size() - 1);
        Vector3 worldCoordPoint = new Vector3(screenX, screenY, 0);
        currentLine.add(worldCoordPoint);

        System.out.println("Unproj: " + cam.unproject(new Vector3(screenX, screenY, 0)));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // End line here
        System.out.println("touchUp!");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Add points to current line
        if(lines.isEmpty()){
            this.touchDown(screenX, screenY, pointer, 0);
        }
        ArrayList<Vector3> currentLine = lines.get(lines.size() - 1);

        Vector3 newPoint = new Vector3(screenX, screenY, 0);
        Vector3 lastPoint = currentLine.get(currentLine.size() - 1);

        //determine squared distance between input and last point
        float lenSq = newPoint.sub(lastPoint).len2();
        System.out.println("lenSq " + lenSq);

        //the minimum distance between input points, squared
        if (lenSq >= minDistSqrd && currentLine.size() < 100) {
            currentLine.add(new Vector3(screenX, screenY, 0));
            System.out.println("Points: " + currentLine.toString());
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
