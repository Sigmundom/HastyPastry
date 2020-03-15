package com.mygdx.hastypastry.views;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.levels.Level1;
import com.mygdx.hastypastry.models.Drawing;

import java.util.ArrayList;

public class DrawView extends BaseView {

    private ShapeRenderer shapeRenderer;
    private Drawing drawing;
    private Level level;

    public DrawView(Assets assets) {
        super(assets);
        drawing = new Drawing();
        controller = new DrawingInputProcessor(spriteViewport.getCamera(), drawing);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void buildStage() {
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        batch.end();
        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLACK);
        for (ArrayList<Vector2> line: drawing.getLines()) {
            for(int i = 0; i < line.size()-1; ++i) {
//                shapeRenderer.rectLine(line.get(i), line.get(i+1), 0.5f);
                shapeRenderer.line(line.get(i), line.get(i+1));
            }
        }
        shapeRenderer.end();
        batch.begin();
    }
}
