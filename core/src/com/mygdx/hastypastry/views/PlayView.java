package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.Waffle;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.ui.MenuButton;

import static com.mygdx.hastypastry.Config.POSITION_ITERATIONS;
import static com.mygdx.hastypastry.Config.TIME_STEP;
import static com.mygdx.hastypastry.Config.VELOCITY_ITERATIONS;

public class PlayView extends BaseView {
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Waffle waffle;
    private RoundObstacle roundObstacle;
    private MenuButton MenuBtn;

    public PlayView(Assets assets) {
        super(assets);
        world = new World(new Vector2(0, -9.81f), false);
        debugRenderer = new Box2DDebugRenderer();
        waffle = new Waffle(assets, world, Config.WORLD_WIDTH/2, Config.WORLD_HEIGHT - 2);
//        roundObstacle = new RoundObstacle(world, new Vector2(50, 50), false);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
//        batch.draw(roundObstacle.getTexture(), roundObstacle.getPos().x, roundObstacle.getPos().y, 2,2);
        batch.draw(waffle.getTexture(), waffle.getX(), waffle.getY(), waffle.getWidth(), waffle.getHeight());
//        debugRenderer.render(world, batch.getProjectionMatrix());
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        update();
    }

    private void update() {
        waffle.update();
    }

    @Override
    public void buildStage() {
        MenuBtn = new MenuButton(assets,"Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.UI_HEIGHT - MenuBtn.getHeight() - 10);
        this.ui.addActor(MenuBtn);
    }
}
