package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.listeners.MyContactListener;
import com.mygdx.hastypastry.models.Drawing;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.models.Waffle;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.levels.Level;

import java.util.ArrayList;

import static com.mygdx.hastypastry.Config.POSITION_ITERATIONS;
import static com.mygdx.hastypastry.Config.TIME_STEP;
import static com.mygdx.hastypastry.Config.VELOCITY_ITERATIONS;

public class SingleplayerView extends BaseView {
    protected Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();;
    protected ShapeRenderer shapeRenderer = new ShapeRenderer();
    protected World world;
    protected MenuButton menuButton;
    protected Drawing drawing;
    protected Level level;

    public SingleplayerView(Assets assets, Level level, Drawing drawing) {
        super(assets);
        world = new World(new Vector2(0, -9.81f), false);
        world.setContactListener(new MyContactListener());
        this.level = level;
        this.drawing = drawing;
        level.getWaffle().addBody(world);
        for (WorldObject obstacle : level.getObstacles()) {
            obstacle.addBody(world);
        }
        drawing.addBody(world);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(); // world step and update positions.

        //Renders obstacles and waffles through levels. Utilizes the sprite draw function, since the sprite already
//        know what it need (position and size).
        for (Obstacle obstacle : level.getObstacles()){
            obstacle.getSprite().draw(batch);
        }
        level.getWaffle().getSprite().draw(batch);

        batch.end();

        // Renders the shape of the bodies. Remove in production.
        debugRenderer.render(world, batch.getProjectionMatrix());

        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLACK);
        for (ArrayList<Vector2> line: drawing.getLines()) {
            for(int i = 0; i < line.size()-1; ++i) {
                shapeRenderer.line(line.get(i), line.get(i+1));
            }
        }
        shapeRenderer.end();
    }

    protected void update() {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            level.getWaffle().update();
    }

    @Override
    public void buildStage() {
        menuButton = new MenuButton(assets,"Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(10, Config.UI_HEIGHT - menuButton.getHeight() - 10);
        this.ui.addActor(menuButton);
    }

}
