package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.PlayButton;
import com.mygdx.hastypastry.ui.UndoButton;

import java.util.List;

public class DrawView extends BaseView {
    private MenuButton menuButton;
    private PlayButton playButton;
    private UndoButton undoButton;
    private ShapeRenderer shapeRenderer;
    private Game game;

    public DrawView(Game game) {
        super();
        this.game = game;
        Box2D.init(); // To be able to make shapes before creating a world.
        shapeRenderer = new ShapeRenderer();
        controller = new DrawingInputProcessor(spriteViewport.getCamera(), game.getPlayer().getDrawing());
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        //Renders obstacles and waffles through levels. Utilizes the sprite draw function, since the sprite already
        //know what it need (position and size).
        for (WorldObject object : game.getWorldObjects()) {
            object.getSprite().draw(batch);
        }
        batch.end();

        Gdx.gl.glLineWidth(3);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setColor(Color.BLACK);
        for (List<Vector2> line: game.getPlayer().getDrawing().getLines()) {
            for(int i = 0; i < line.size()-1; ++i) {
                shapeRenderer.line(line.get(i), line.get(i+1));
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void buildStage() {
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(10, Config.UI_HEIGHT - menuButton.getHeight() - 10);
        playButton = new PlayButton();
        playButton.setPosition(Config.UI_WIDTH - playButton.getWidth() - 10,
                Config.UI_HEIGHT - playButton.getHeight() - 10);
        playButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.isMultiplayer()) {
//                            game.getPlayer().getDrawing().uploadLines(game.getGameID(), game.getPlayer().getName());
                        } else {
                            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY, game);
                        }
                        return false;
                    }
                });
        undoButton = new UndoButton();
        undoButton.setPosition(10,
                Config.UI_HEIGHT - undoButton.getHeight() - menuButton.getHeight() - 15);
        undoButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        game.getPlayer().getDrawing().undoLine();
                        return false;
                    }
                });
        this.ui.addActor(menuButton);
        this.ui.addActor(playButton);
        this.ui.addActor(undoButton);
    }
}
