package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.DrawingInputProcessor;

public abstract class BaseView implements Screen {
    protected Assets assets;
    protected DrawingInputProcessor controller;
    protected Stage ui;
    protected Texture background = new Texture("bg.png");
    protected SpriteBatch batch;
    protected Viewport spriteViewport;

    public BaseView(Assets assets, boolean... controllerBools) {
        this.assets = assets;
        batch = new SpriteBatch();
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        spriteViewport = new FitViewport(Config.WORLD_WIDTH, Config.WORLD_HEIGHT, camera); //to draw sprites
        FitViewport stageViewport = new FitViewport(Config.UI_WIDTH, Config.UI_HEIGHT); //to draw actors
        ui = new Stage(stageViewport, new SpriteBatch()); // The stage will contain UI elements
        System.out.println("length: " + controllerBools.length);
        if (controllerBools.length == 1 && controllerBools[0]) {
            this.controller = new DrawingInputProcessor(camera); //For example drawingController
        }
    }

    // Subclasses must load actors in this method
    public abstract void buildStage();

    @Override
    public void render(float delta) {
        // Clear screen
        spriteViewport.apply(); //Set the world viewport.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(spriteViewport.getCamera().combined);
        batch.begin();
        // custom drawing
        batch.draw(background, 0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        draw(batch, delta);
        batch.end();

        // draw ui
        if (ui != null) {
            ui.getViewport().apply(); //Set the UI viewport
//            ui.act(delta); // Don't think we need this because UI is static.
            ui.draw();
        }
    }

    /**
     * Override this sucker to implement any custom drawing
     *
     * @param delta The number of seconds that have passed since the last frame.
     * @param batch Spritebatch to draw new models/sprites.
     */
    public void draw(SpriteBatch batch, float delta) {
    }

    @Override
    public void show() {
        InputMultiplexer input = new InputMultiplexer(); //To handle 2 controllers at once.
        // Add controllers related to ui
        input.addProcessor(ui);
        if (controller != null) {
            // Add custom controller like lobby or drawing controller
            input.addProcessor(controller);
        }
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void resize(int width, int height) {
        ui.getViewport().update(width, height, true);
        spriteViewport.update(width, height, true);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        if (ui != null) ui.dispose();
        ui = null;
    }
}
