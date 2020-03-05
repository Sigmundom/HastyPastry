package com.mygdx.hastypastry.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.models.Waffle;

public class PlayState extends GameState{

    private OrthographicCamera cam;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Waffle waffle;

    public PlayState(GameStateManager gsm, float start_x, float start_y) {
        super(gsm);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        world = new World(new Vector2(0, -9.81f), false);
        b2dr = new Box2DDebugRenderer();
        waffle = new Waffle(world, start_x, start_y);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        world.step(1/60f, 6,2 );

    }

    @Override
    public void render(SpriteBatch sb) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, cam.combined);
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
    }
}
