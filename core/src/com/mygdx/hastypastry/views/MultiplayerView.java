package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Drawing;

import java.util.ArrayList;
import java.util.List;

public class MultiplayerView extends SingleplayerView {
    private Drawing opponentDrawing;

    public MultiplayerView(Level level, Drawing drawing, Drawing opponentDrawing) {
        super(level, drawing);
        this.opponentDrawing = opponentDrawing;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(); // world step and update positions.

        //Renders obstacles and waffles through levels. Utilizes the sprite draw function, since the sprite already
        //know what it need (position and size).
//        for (Obstacle obstacle : level.getObstacles()){
//            obstacle.getSprite().draw(batch);
//        }
//        level.getWaffle().getSprite().draw(batch);

        batch.end();

        // Renders the shape of the bodies. Remove in production.
        debugRenderer.render(world, batch.getProjectionMatrix());

        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLACK);
        for (List<Vector2> line: drawing.getLines()) {
            for(int i = 0; i < line.size()-1; ++i) {
                shapeRenderer.line(line.get(i), line.get(i+1));
            }
        }
        shapeRenderer.end();
    }
}
