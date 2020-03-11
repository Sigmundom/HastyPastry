package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.Waffle;
import com.mygdx.hastypastry.ui.MenuButton;

import java.awt.Menu;

import static com.mygdx.hastypastry.Config.POSITION_ITERATIONS;
import static com.mygdx.hastypastry.Config.SCALE;
import static com.mygdx.hastypastry.Config.TIME_STEP;
import static com.mygdx.hastypastry.Config.VELOCITY_ITERATIONS;

public class PlayView extends BaseView {
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera orthographicCamera;
    private World world;
    private Waffle waffle;
    private RoundObstacle roundObstacle;
    private MenuButton MenuBtn;

    public PlayView() {
        super();
        world = new World(new Vector2(0, -9.81f), false);
        orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth()/SCALE, Gdx.graphics.getHeight()/SCALE);
        b2dr = new Box2DDebugRenderer();
        waffle = new Waffle(world, Config.WIDTH/2, Config.HEIGHT - 50);
        roundObstacle = new RoundObstacle(world, new Vector2(50, 50), false);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        batch.setProjectionMatrix(orthographicCamera.combined);
        batch.draw(roundObstacle.getTexture(), roundObstacle.getPos().x, roundObstacle.getPos().y, 45, 44);
        waffle.draw(batch);
    }



    @Override
    public void buildStage() {
        MenuBtn = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.HEIGHT - MenuBtn.getHeight() - 10);
        this.ui.addActor(MenuBtn);
    }
}
