package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;


/* The Drawing is an object containing all the lines as drawn in the GameView
 * It provides functionality to add lines and points, which are used by DrawingInputProcessor
 * addBodies(world) us used bu GameView for adding the bodies to the world when the user pushes play-btn.
 * GameView ask for all lines for drawing

 * The Drawing is an object containing all the lines as drawn in the DrawView
 * @author sigmundhh */
public class Drawing {

    private Stack<List<Vector3>> lines = new Stack<>();
    private List<Body> bodies;
    private Inkbar inkbar;
    private boolean isPlayer;

    public Drawing(int inkLimit, boolean isPlayer){
        this.isPlayer = isPlayer;
        this.inkbar = new Inkbar(inkLimit);
    }

    public void addLine(List<Vector3> line) {
        if(inkbar.getInkLeft() > 1){
            lines.push(line);
        }
    }

    public void addPoint(Vector3 point) {
        if(inkbar.inkbarCheck()){
            System.out.println("Adding: " + point);
            inkbar.useInk(point.z);
            lines.peek().add(point);
        }
    }

    public void undoLine(){
            if(!lines.isEmpty()){
                for (Vector3 point : lines.pop()) {
                    inkbar.refillInk(point.z); // Z-component represents the ink used for each point
                }
        }
    }

    public void addBody(World world) {
        bodies = new ArrayList<>();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.filter.categoryBits = (short) (isPlayer ? 4 : 8);
        fixtureDef.filter.maskBits = 1;

        for (List<Vector3> line : lines) {
            Shape shape;
            if (line.size() == 1) {
                shape = new CircleShape();
                shape.setRadius(0.1f);
                bodyDef.position.set(line.get(0).x, line.get(0).y);
            } else {
                int lineSize = line.size();

                if (line.get(lineSize-1).idt(line.get(lineSize-2))) {
                    // To fix a bug where the last point in a line often is added twice
                    line.remove(lineSize-1);
                    lineSize -= 1;
                }

                Vector2[] vertices = new Vector2[lineSize];
                for (int i=0; i < lineSize; i++) {
                    vertices[i] = new Vector2(line.get(i).x, line.get(i).y);
                };
                shape = new ChainShape();
                ((ChainShape)shape).createChain(vertices);
            }
            fixtureDef.shape = shape;
            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);
            body.setUserData(false); //Not dangerous to collide with
            bodies.add(body);
            shape.dispose();
        }
    }

    public List<List<String>> serializeLines() {
        List<List<String>> serializedLines = new ArrayList<>();

        for (List<Vector3> line : lines) {
            List<String> currentLine = new ArrayList<>();
            for (Vector3 point : line) {
                currentLine.add(point.toString());
            }
            serializedLines.add(currentLine);
        }
        if (serializedLines.size() == 0) {
            // Empty drawing
            List<String> emptyLine = new ArrayList<>();
            emptyLine.add("(-1,-1, -1)");
            serializedLines.add(emptyLine);
        }
        return serializedLines;
    }

    public void uploadLines(String gameID, String playerName) {
        GdxFIRDatabase.inst()
                .inReference(String.format("games/%s/%s/drawing", gameID, playerName))
                .setValue(serializeLines());
    }


    public Stack<List<Vector3>> getLines() {
        return lines;
    }

    public Inkbar getInkbar() {
        return inkbar;
    }

    public void deserializeDrawing(List<List<String>> opponentDrawing) {
        System.out.println(opponentDrawing);
        for (List<String> serializedLine : opponentDrawing) {
            List<Vector3> deserializedLine = new ArrayList<>();
            for (String serializedPoint : serializedLine) {
                Vector3 deserializedPoint = new Vector3().fromString(serializedPoint);
                deserializedLine.add(deserializedPoint);
            }
            lines.push(deserializedLine);
        }
    }
}
