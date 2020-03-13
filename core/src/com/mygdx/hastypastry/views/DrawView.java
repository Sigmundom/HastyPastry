package com.mygdx.hastypastry.views;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.models.Drawing;

public class DrawView extends BaseView {

    private Drawing drawing;

    public DrawView(Assets assets) {
        super(assets);
        drawing = new Drawing(spriteViewport);
        controller = new DrawingInputProcessor(spriteViewport.getCamera(), drawing);
    }

    @Override
    public void buildStage() {
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        drawing.draw();
    }
}
