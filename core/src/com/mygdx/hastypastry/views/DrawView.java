package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.ComposedImage;
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
    ProgressBar inkBar;
    ProgressBar timeBar;

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
        Table menu = new Table();
        menu.setFillParent(true);
        menu.top();

        Skin skin = Assets.instance.getManager().get(Assets.orangeUiSkin);
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle(skin.get("default", ImageButton.ImageButtonStyle.class));
        buttonStyle.imageUp = new TextureRegionDrawable(Assets.instance.getManager().get(Assets.uiAtlas).findRegion("undo"));
        ImageButton undo = new ImageButton(buttonStyle);
        menu.add(undo).size(50,50).padRight(20);

        TextureAtlas orangeAtlas = Assets.instance.getManager().get(Assets.orangeUiAtlas);
        TextureAtlas uiAtlas = Assets.instance.getManager().get(Assets.uiAtlas);




        float inkLimit = game.getLevel().getInkLimit();
        timeBar = new ProgressBar(0, 60, 1, false, skin, "default");
        timeBar.getStyle().knobBefore=null;
        timeBar.getStyle().background.setMinHeight(20);
//        timeBar.setScaleY(2f);
        inkBar = new ProgressBar(0, inkLimit, 1, false, skin, "default");
//        inkBar.setScaleY(2f);

        ComposedImage inkLabel = new ComposedImage("ink");
        ComposedImage timeLabel = new ComposedImage("time");

        Table table = new Table();
        table.pad(10);
        table.add(inkLabel).size(20,20).padBottom(5);
        table.add(inkBar).growY();
        table.row();
        table.add(timeLabel).size(20,20).padTop(5);
        table.add(timeBar).growY();
        menu.add(table);

//        VerticalGroup verticalGroup = new VerticalGroup();
//        verticalGroup.space(10);
//        HorizontalGroup horizontalGroup1 = new HorizontalGroup();
//        horizontalGroup1.setHeight(15);
//        HorizontalGroup horizontalGroup2 = new HorizontalGroup();
//
//        horizontalGroup1.addActor(inkLabel);
//        horizontalGroup1.addActor(inkBar);
//        horizontalGroup2.addActor(timeLabel);
//        horizontalGroup2.addActor(timeBar);
//
//        verticalGroup.addActor(horizontalGroup1);
//        verticalGroup.addActor(horizontalGroup2);
//
//        menu.add(verticalGroup);

        ImageButton play = new ImageButton(skin, "right");
        menu.add(play).padLeft(20);
//
        ui.addActor(menu);

//        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
//        menuButton.setPosition(10, Config.UI_HEIGHT - menuButton.getHeight() - 10);
//        playButton = new PlayButton();
//        playButton.setPosition(Config.UI_WIDTH - playButton.getWidth() - 10,
//                Config.UI_HEIGHT - playButton.getHeight() - 10);
//        playButton.addListener(
//                new InputListener() {
//                    @Override
//                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                        if (game.isMultiplayer()) {
//                            game.ready();
//                        } else {
//                            ScreenManager.getInstance().showScreen(ScreenEnum.PLAY, game);
//                        }
//                        return false;
//                    }
//                });
//        undoButton = new UndoButton();
//        undoButton.setPosition(10,
//                Config.UI_HEIGHT - undoButton.getHeight() - menuButton.getHeight() - 15);
//        undoButton.addListener(
//                new InputListener() {
//                    @Override
//                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                        game.getPlayer().getDrawing().undoLine();
//                        return false;
//                    }
//                });
//
//        ProgressBar progressBar = new ProgressBar(0f, (float)game.getLevel().getInkLimit(), 1.0f, true, skin, "default-vertical");
//        progressBar.setPosition(5, 5);
//        progressBar.setSize(30, Config.UI_HEIGHT - 100);
//        progressBar.setValue((float)game.getLevel().getInkLimit());
//
//        this.ui.addActor(progressBar);
//        this.ui.addActor(menuButton);
//        this.ui.addActor(playButton);
//        this.ui.addActor(undoButton);
    }
}
