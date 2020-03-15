package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.listeners.MyContactListener;
import com.mygdx.hastypastry.models.Drawing;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.levels.Level1;
import com.mygdx.hastypastry.ui.PlayButton;

import java.util.ArrayList;

import static com.mygdx.hastypastry.Config.POSITION_ITERATIONS;
import static com.mygdx.hastypastry.Config.TIME_STEP;
import static com.mygdx.hastypastry.Config.VELOCITY_ITERATIONS;

public class PlayView extends BaseView {
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private World world;
    private MenuButton menuButton;
    private PlayButton playButton;
    private Drawing drawing;
    private Level level;
    private boolean paused = true;

    public PlayView(Assets assets) {
        super(assets);
        world = new World(new Vector2(0, -9.81f), false);
        world.setContactListener(new MyContactListener());
        level = new Level1(assets, world);
        drawing = new Drawing();
        controller = new DrawingInputProcessor(spriteViewport.getCamera(), drawing);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        update(); // world step and update positions.

        //Renders obstacles and waffles through levels. Utilizes the sprite draw function, since the sprite already
        //know what it need (position and size).
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
        batch.begin();

    }

    private void update() {
        if (!paused) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            level.getWaffle().update();
        }
    }

    @Override
    public void buildStage() {
        menuButton = new MenuButton(assets,"Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(10, Config.UI_HEIGHT - menuButton.getHeight() - 10);
        playButton = new PlayButton(assets);
        playButton.setPosition(Config.UI_WIDTH - playButton.getWidth() - 10,
                Config.UI_HEIGHT - playButton.getHeight() - 10);
        playButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        drawing.addBodies(world);
                        paused = false;
                        return false;
                    }
                });
        this.ui.addActor(menuButton);
        this.ui.addActor(playButton);
    }

    @Override
    public void resume() {
        paused = false;
    }
}
