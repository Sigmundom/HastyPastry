package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.SquareObstacle;
import com.mygdx.hastypastry.models.TriangularObstacle;
import com.mygdx.hastypastry.models.Waffle;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.ui.MenuButton;

import java.util.ArrayList;

import static com.mygdx.hastypastry.Config.POSITION_ITERATIONS;
import static com.mygdx.hastypastry.Config.TIME_STEP;
import static com.mygdx.hastypastry.Config.VELOCITY_ITERATIONS;

public class PlayView extends BaseView {
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Waffle waffle;
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private MenuButton MenuBtn;

    public PlayView(Assets assets) {
        super(assets);
        world = new World(new Vector2(0, -9.81f), false);
        debugRenderer = new Box2DDebugRenderer();
        waffle = new Waffle(assets, world, Config.WORLD_WIDTH/2, Config.WORLD_HEIGHT - 2);
        obstacles.add(new RoundObstacle(assets, world, Config.WORLD_WIDTH/2, 2, 2, false));
        obstacles.add(new SquareObstacle(assets, world, 3, 8, 2, 4, false));
        obstacles.add(new TriangularObstacle(assets, world, 10,10,6,3, false));
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
//        debugRenderer.render(world, batch.getProjectionMatrix());
        for (Obstacle obstacle : obstacles) {
            obstacle.getSprite().draw(batch);
        }
        waffle.getSprite().draw(batch);
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
