package com.mygdx.hastypastry.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.Waffle;

public class PlayState extends GameState{

    private OrthographicCamera cam;
    private Box2DDebugRenderer b2dr;
    private World world;
    private Waffle waffle;
    private RoundObstacle roundObstacle;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        cam = new OrthographicCamera();
        cam.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        world = new World(new Vector2(0, -9.81f), false);
        b2dr = new Box2DDebugRenderer();
        waffle = new Waffle(world, 200, 200);
        roundObstacle = new RoundObstacle(world, new Vector2(50, 50), false);
        waffle = new Waffle(world, 200, 200);
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

        sb.begin();
//        sb.draw(roundObstacle.getTexture(), roundObstacle.getPos().x, roundObstacle.getPos().y, 45, 44);
//        sb.draw(waffle.getWaffleTexture(), waffle.getWaffle().getPosition().x*32-waffle.getWaffleTexture().getWidth()/2f,
//                waffle.getWaffle().getPosition().y*32-waffle.getWaffleTexture().getHeight(), 20, 50);
        sb.end();

        b2dr.render(world, cam.combined.scl(32));
    }

    @Override
    public void dispose() {
        b2dr.dispose();
        world.dispose();
        roundObstacle.dispose();
        waffle.dispose();
    }
}
