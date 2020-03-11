package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.hastypastry.Config;

public abstract class BaseView implements Screen {
    protected InputProcessor controller;
    protected Stage ui = new Stage(new FitViewport(Config.WIDTH, Config.HEIGHT));
    protected Texture background = new Texture("bg.png");
    protected SpriteBatch spriteBatch = new SpriteBatch();

    public BaseView() {}

    public BaseView(InputProcessor controller) {
        this.controller = controller;
    }

    // Subclasses must load actors in this method
    public abstract void buildStage();

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(background, 0,0, Config.WIDTH, Config.HEIGHT);
        // custom drawing
        draw(spriteBatch, delta);
        spriteBatch.end();

        // draw ui
        if(ui != null) {
            ui.act(delta);
            ui.draw();
        }
    }

    /**
     * Override this sucker to implement any custom drawing
     * @param delta The number of seconds that have passed since the last frame.
     * @param spriteBatch Spritebatch to draw new models/sprites.
     */
    public void draw(SpriteBatch spriteBatch, float delta) {}

    @Override
    public void show() {
        InputMultiplexer input = new InputMultiplexer();
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
    }

    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override public void dispose() {
        if(ui != null) ui.dispose();
        ui = null;
    }
}
