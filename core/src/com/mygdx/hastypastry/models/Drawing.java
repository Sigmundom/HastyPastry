package com.mygdx.hastypastry.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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

    private Stack<List<Vector2>> lines = new Stack<>();
    private List<Body> bodies;
    private Inkbar inkbar;
    private boolean isPlayer;

    public Drawing(int inkLimit, boolean isPlayer){
        this.isPlayer = isPlayer;
        this.inkbar = new Inkbar(inkLimit);
    }

    public void addLine(List<Vector2> line) {
        if(inkbar.inkbarCheck()){
            lines.push(line);
        }

    }

    public void addPoint(Vector2 point) {
        if(inkbar.inkbarCheck()){
            lines.peek().add(point);
            inkbar.useInk();
        }
    }

    public void undoLine(){
            if(!lines.isEmpty()){
            inkbar.refillInk(lines.pop().size());
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

        for (List<Vector2> line : lines) {
            Body body = world.createBody(bodyDef);
            Vector2[] vertices = new Vector2[line.size()];
            line.toArray(vertices);
            ChainShape shape = new ChainShape();
            shape.createChain(vertices);
            fixtureDef.shape = shape;

            body.createFixture(fixtureDef);
            body.setUserData(false); //Not dangerous to collide with
            bodies.add(body);
            shape.dispose();
        }
    }

    public List<List<String>> serializedLines() {
        List<List<String>> serializedLines = new ArrayList<>();

        for (List<Vector2> line : lines) {
            List<String> currentLine = new ArrayList<>();
            for (Vector2 point : line) {
                currentLine.add(point.toString());
            }
            serializedLines.add(currentLine);
        }
        return serializedLines;
    }

    public void uploadLines(String gameID, String playerName) {
        GdxFIRDatabase.inst()
                .inReference(String.format("games/%s/%s/drawing", gameID, playerName))
                .setValue(serializedLines());
    }


    public Stack<List<Vector2>> getLines() {
        return lines;
    }


    public void deserializeDrawing(List<List<String>> opponentDrawing) {
        System.out.println(opponentDrawing);
        for (List<String> serializedLine : opponentDrawing) {
            List<Vector2> deserializedLine = new ArrayList<>();
            for (String serializedPoint : serializedLine) {
                Vector2 deserializedPoint = new Vector2().fromString(serializedPoint);
                deserializedLine.add(deserializedPoint);
            }
            lines.push(deserializedLine);
        }
    }
}
