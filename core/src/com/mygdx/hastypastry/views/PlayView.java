package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.Waffle;

public class PlayView extends BaseView {
    private Box2DDebugRenderer b2dr;
    private World world;
    private Waffle waffle;
    private RoundObstacle roundObstacle;

    public PlayView() {
        super();
        world = new World(new Vector2(0, -9.81f), false);
        b2dr = new Box2DDebugRenderer();
        waffle = new Waffle(world, Config.WIDTH/2, Config.HEIGHT - 50);
        roundObstacle = new RoundObstacle(world, new Vector2(50, 50), false);
    }

    @Override
    public void draw(SpriteBatch spriteBatch, float delta) {
        spriteBatch.draw(roundObstacle.getTexture(), roundObstacle.getPos().x, roundObstacle.getPos().y, 45, 44);
        waffle.draw(spriteBatch);
    }
    @Override
    public void buildStage() {

    }
}
