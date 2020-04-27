package com.mygdx.hastypastry.views;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.StyledTextButton;

public class FailedLevelView extends AbstractView {

    private Game game;
    private MenuButton menuButton;
    private BitmapFont titleFont = new BitmapFont();
    private BitmapFont font = new BitmapFont();
    private Table table;
    private Label failedLabel;
    private Label levelLabel;
    private Label descLabel;
    private Sound buttonSound;

    public FailedLevelView(Game game) {
        super();
        buttonSound = MusicAndSound.instance.getButtonSound();
        this.game = game;
    }


    @Override
    public void draw(SpriteBatch batch, float delta){

        failedLabel.setText("Failed");
        levelLabel.setText("Try " + game.getLevel().getLevel() + " again!");

        // Tips
        descLabel.setText("Tips: \nDon't hit the \nred obstacles. \nThey are deadly!");
    }

    @Override
    public void buildStage() {
        // Set font parameters
        titleFont = generateFont("pixelfont.TTF", 36);
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle(titleFont, Color.BLACK);

        font = generateFont("pixelfont.TTF", 20);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        failedLabel = new Label("Failed", titleLabelStyle);
        levelLabel = new Label("LevelID", labelStyle);
        descLabel = new Label("Desc", labelStyle);

        table = new Table();
        //table.top().padLeft(Config.UI_WIDTH/2 - 100).left().padTop(10);
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(failedLabel);
        table.row();
        table.add(levelLabel);
        table.row();
        table.add(descLabel).padTop(200);

        // Sound effects
        if(PlayerPreferences.instance.isMusicEnabled()) {
            MusicAndSound.instance.getGameMusic().setVolume(PlayerPreferences.instance.getMusicVolume());
        }

        // Create menu button
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);

        // Create replay-button
        MenuButton replayButton = new MenuButton("  Replay level  ", ScreenEnum.DRAW, new Game(new Level(game.getLevel().getLevel())));
        replayButton.setPosition(Config.UI_WIDTH/2 - replayButton.getWidth()/2 - 90, Config.UI_HEIGHT/2 - 220);


        // Creating high score button, sending game through to high score list.
        StyledTextButton highScoreButton = new StyledTextButton("  High Score  ");

        highScoreButton.setWidth(replayButton.getWidth());
        highScoreButton.setPosition(Config.UI_WIDTH/2 - highScoreButton.getWidth()/2 + 90, Config.UI_HEIGHT/2 - 220);
        highScoreButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
                            buttonSound.play(0.5f);
                        }
                        if (game.isMultiplayer()) {
                            ScreenManager.getInstance().showScreen(ScreenEnum.LEADERBOARD, game);
                        } else {
                            ScreenManager.getInstance().showScreen(ScreenEnum.HIGHSCORE, game);
                        }
                        return false;
                    }
                });

        // Set width and position of menu button
        menuButton.setWidth(replayButton.getWidth());
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 300);

        // Add buttons to the stage
        this.ui.addActor(menuButton);
        this.ui.addActor(replayButton);
        this.ui.addActor(highScoreButton);
        this.ui.addActor(table);
    }
}
