package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.hastypastry.controllers.DrawingInputProcessor;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.interfaces.WorldObject;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.MusicAndSound;

import java.util.List;

public class DrawView extends BaseView {
    private ShapeRenderer shapeRenderer;
    private Game game;
    private ProgressBar inkbar;
    private ProgressBar timebar;
    private PlayerPreferences playerPreferences;
    private Sound buttonSound;
    private float timeLeft = 60;

    public DrawView(Game game) {
        super();
        this.game = game;
        Box2D.init(); // To be able to make shapes before creating a world.
        shapeRenderer = new ShapeRenderer();
        controller = new DrawingInputProcessor(spriteViewport.getCamera(), game.getPlayer().getDrawing());
        playerPreferences = new PlayerPreferences();
        buttonSound = MusicAndSound.instance.getButtonSound();
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
    }

    @Override
    public void buildStage() {
        // References to resources
        Skin skin = Assets.instance.getManager().get(Assets.orangeUiSkin);

        // UndoButton
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle(skin.get("default", ImageButton.ImageButtonStyle.class));
        buttonStyle.imageUp = new TextureRegionDrawable(Assets.instance.getManager().get(Assets.uiAtlas).findRegion("undo"));
        ImageButton undo = new ImageButton(buttonStyle);
        undo.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(playerPreferences.isSoundEffectsEnabled()) {
                            buttonSound.play();
                        }
                        game.getPlayer().getDrawing().undoLine();
                        return false;
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
        timebar = new ProgressBar(0, 30, 1, false, skin, "default");
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
        ImageButton play = new ImageButton(skin, "right");
        play.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(playerPreferences.isSoundEffectsEnabled()) {
                            buttonSound.play();
                        }
                        game.ready();
                        return false;
                    }
                });

        // Initialize topMenu
        Table topMenu = new Table();
        topMenu.setFillParent(true);
        topMenu.top();

        // Add components to topMenu
        topMenu.add(undo).size(50,50).padRight(20);
        topMenu.add(barTable);
        topMenu.add(play).padLeft(20);

        // Add topMenu to ui
        ui.addActor(topMenu);
    }
}
