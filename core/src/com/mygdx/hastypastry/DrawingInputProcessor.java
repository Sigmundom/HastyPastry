package com.mygdx.hastypastry;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DrawingInputProcessor implements InputProcessor {

    private ArrayList<Vector2> points = new ArrayList<Vector2>();
    private static int minDistSqrd = 2;




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

        Vector2 newPoint = new Vector2(screenX, screenY);
        Vector2 lastPoint = points.get(points.size() - 1);

        //determine squared distance between input and last point
        float lenSq = newPoint.sub(lastPoint).len2();

        //the minimum distance between input points, squared
        if (lenSq >= minDistSqrd && points.size() > 10) {
            points.add(new Vector2(screenX, screenY));
        }
        System.out.println(" " + points.toString());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
