package com.mygdx.hastypastry.views;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.DrawingInputProcessor;
import com.mygdx.hastypastry.models.Drawing;

public class DrawView extends BaseView {

    private Drawing drawing;
    private World world;

    public DrawView(Assets assets) {
        super(assets,true);
        world = new World(new Vector2(0, -9.81f), false);
        this.drawing = new Drawing(world, this.controller);
    }

    @Override
    public void buildStage() {
    }
}
