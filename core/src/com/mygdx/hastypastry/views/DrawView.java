package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.levels.Level1;
import com.mygdx.hastypastry.models.Drawing;
import com.mygdx.hastypastry.models.GameInfo;
import com.mygdx.hastypastry.models.Obstacle;
import com.mygdx.hastypastry.models.Player;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.PlayButton;

import java.util.ArrayList;

public class DrawView extends BaseView {
    private MenuButton menuButton;
    private PlayButton playButton;
    private ShapeRenderer shapeRenderer;
    private Level level;
    private Drawing drawing;
    private GameInfo gameInfo;

    public DrawView(Assets assets, Object... params) {
        super(assets);
        if (params.length == 1) {
            gameInfo = (GameInfo)params[0];
        }
        Box2D.init(); // To be able to make shapes before creating a world.
        level = new Level1(assets);
        shapeRenderer = new ShapeRenderer();
        drawing = new Drawing();
        controller = new DrawingInputProcessor(spriteViewport.getCamera(), drawing);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        //Renders obstacles and waffles through levels. Utilizes the sprite draw function, since the sprite already
        //know what it need (position and size).
        for (Obstacle obstacle : level.getObstacles()){
            obstacle.getSprite().draw(batch);
        }
        level.getWaffle().getSprite().draw(batch);

        batch.end();

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
                        ScreenManager.getInstance().showScreen(ScreenEnum.PLAY, level, drawing);
                        return false;
                    }
                });
        this.ui.addActor(menuButton);
        this.ui.addActor(playButton);
    }
}
