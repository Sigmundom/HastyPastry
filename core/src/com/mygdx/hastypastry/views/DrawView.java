package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.listeners.MyButtonListener;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.MenuButton;

import java.util.List;

public class DrawView extends AbstractView {
    private ShapeRenderer shapeRenderer;
    private Game game;
    private ProgressBar inkbar;
    private ProgressBar timebar;
    private float timeLeft;

    public DrawView(Game game) {
        super();
        this.game = game;
        this.timeLeft = game.getLevel().getTimeLimit();
        Box2D.init(); // To be able to make shapes before creating a world.
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        updateBars(delta);
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
        for (List<Vector3> line: game.getPlayer().getDrawing().getLines()) {
            if (line.size() == 1) {
                shapeRenderer.circle(line.get(0).x, line.get(0).y, 0.1f);
            } else {
                for(int i = 0; i < line.size()-1; ++i) {
                    shapeRenderer.line(line.get(i).x, line.get(i).y, line.get(i+1).x, line.get(i+1).y);
                }
            }
        }
        shapeRenderer.end();
    }

    private void updateBars(float delta) {
        // Update timebar
        timeLeft -= delta;
        timebar.setValue(timeLeft);

        if (timeLeft < 0) {
            game.ready();
        }

        // Update inkbar
        inkbar.setValue(game.getPlayer().getDrawing().getInkbar().getInkLeft());
//        if (game.getPlayer().getDrawing().getInkbar().getInkLeft() < 0.5f) {
//            inkbar.getStyle().knobBefore = null;
//        }
    }

    @Override
    public void buildStage() {
        // References to resources
        final Skin skin = Assets.instance.getManager().get(Assets.orangeUiSkin);

        // UndoButton
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle(skin.get("default", ImageButton.ImageButtonStyle.class));
        buttonStyle.imageUp = new TextureRegionDrawable(Assets.instance.getManager().get(Assets.uiAtlas).findRegion("undo"));
        ImageButton undo = new ImageButton(buttonStyle);

        // Sound effects
        if(PlayerPreferences.instance.isMusicEnabled()) {
            MusicAndSound.instance.getGameMusic().setVolume(PlayerPreferences.instance.getMusicVolume() * 0.2f);
        }

        undo.addListener(new MyButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.getPlayer().getDrawing().undoLine();
            }
        });

        // Inkbar with ink icon
        float inkLimit = game.getLevel().getInkLimit();
        inkbar = new ProgressBar(0, inkLimit, 0.1f, false, skin, "default");
        inkbar.setValue(inkLimit);
        inkbar.getStyle().background.setMinHeight(20);
        inkbar.getStyle().knobBefore.setMinHeight(20);

        Image ink = new Image(Assets.instance.getManager().get(Assets.uiAtlas).findRegion("ink"));
        ink.setSize(15,15);

        // Timebar with clock icon
        timebar = new ProgressBar(0, game.getLevel().getTimeLimit(), 1, false, skin, "default");
        timebar.setValue(timeLeft);

        Image clock = new Image(Assets.instance.getManager().get(Assets.uiAtlas).findRegion("time"));
        clock.setSize(15,15);

        // Table to structure the time- and inkbar.
        Table barTable = new Table();
        barTable.pad(10);
        barTable.add(timebar).growY().padBottom(2);
        barTable.add(clock).size(15,15).padLeft(-20);
        barTable.row();
        barTable.add(inkbar).growY().padTop(2);
        barTable.add(ink).size(15,15).padLeft(-20);

        // Play button
        final ImageButton play = new ImageButton(skin, "right");
        play.addListener(new MyButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                play.setTouchable(Touchable.disabled);
                play.setColor(1,1,1,0.5f);
                game.ready();
            }
        });

        // Initialize topMenu
        Table topMenu = new Table();
        topMenu.setFillParent(true);
        topMenu.top();


        // Add components to topMenu
        int padValue;
        if (game.isMultiplayer()) {
            padValue = 20;
        } else {
            padValue = 10;
            MenuButton menu = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
            topMenu.add(menu).padRight(padValue);
        }
        topMenu.add(undo).size(50,50).padRight(padValue);
        topMenu.add(barTable).padRight(padValue);
        topMenu.add(play);

        // Add topMenu to ui
        ui.addActor(topMenu);
    }

    @Override
    public void show() {
        InputMultiplexer input = new InputMultiplexer(); //To handle 2 controllers at once.
        // Add controllers related to ui
        input.addProcessor(ui);
        // Add custom controller like lobby or drawing controller
        input.addProcessor(new DrawingInputProcessor(spriteViewport.getCamera(), game.getPlayer().getDrawing()));
        Gdx.input.setInputProcessor(input);
    }
}
