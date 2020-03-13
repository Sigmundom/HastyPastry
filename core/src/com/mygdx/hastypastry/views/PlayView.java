package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.listeners.MyContactListener;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.models.RoundObstacle;
import com.mygdx.hastypastry.models.SquareObstacle;
import com.mygdx.hastypastry.models.TriangularObstacle;
import com.mygdx.hastypastry.models.Waffle;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.levels.Level1;
import java.util.ArrayList;

import static com.mygdx.hastypastry.Config.POSITION_ITERATIONS;
import static com.mygdx.hastypastry.Config.TIME_STEP;
import static com.mygdx.hastypastry.Config.VELOCITY_ITERATIONS;

public class PlayView extends BaseView {
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private MenuButton MenuBtn;
    private Level level;

    public PlayView(Assets assets) {
        super(assets);
        world = new World(new Vector2(0, -9.81f), false);
        world.setContactListener(new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();
        level = new Level1(assets, world);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        // Uncomment this to see bodies. For some reason I can't seem to render both textures and bodies
//        debugRenderer.render(world, batch.getProjectionMatrix());

        //Renders obstacles and waffles through levels. Utilizes the sprite draw function, since the sprite already
        //know what it need (position and size).
        for (Obstacle obstacle : level.getObstacles()){
            obstacle.getSprite().draw(batch);
        }
        level.getWaffle().getSprite().draw(batch);
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        update();
    }

    private void update() {
        level.getWaffle().update();
    }

    @Override
    public void buildStage() {
        MenuBtn = new MenuButton(assets,"Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.UI_HEIGHT - MenuBtn.getHeight() - 10);
        this.ui.addActor(MenuBtn);
    }
}
