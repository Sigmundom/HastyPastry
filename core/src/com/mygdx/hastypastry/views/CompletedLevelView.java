package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.HighScoreButton;
import com.mygdx.hastypastry.ui.LabelButton;
import com.mygdx.hastypastry.ui.MenuButton;

import java.text.DecimalFormat;

public class CompletedLevelView extends BaseView {
    private Game game;
    private MenuButton menuButton;
    private HighScoreButton highScoreButton;
    protected BitmapFont font;
    private Table table;
    protected Label completedLabel;
    protected Label levelLabel;
    protected Label timeLabel;
    protected float levelTime;
    protected DecimalFormat df = new DecimalFormat("###.##");
    private Texture texture = new Texture("star.png");
    private Sprite sprite = new Sprite(texture);
    private float rotationDegree;
    protected Label newHighScoreLabel;
    protected PlayerPreferences playerPreferences;


    public CompletedLevelView(Game game) {
        super();
        this.game = game;
        sprite.setSize(Config.UI_WIDTH/80, Config.UI_WIDTH/80);
        sprite.setOrigin(sprite.getWidth()/2,-15);
        playerPreferences = new PlayerPreferences();
    }

    @Override
    public void draw(SpriteBatch batch, float delta){
        rotationDegree = 15.0f;
        for(float ranking : game.getLevel().getStarRank()) {
            if(levelTime < ranking) {
                sprite.draw(batch);
                sprite.setPosition(Config.WORLD_WIDTH/2 - sprite.getWidth()/2, Config.WORLD_HEIGHT/2);
                sprite.setRotation(rotationDegree);
                rotationDegree -= 15.0f;
            }
        }
        batch.end();

        levelTime = game.getPlayer().getNewLevelTime();
        completedLabel.setText("Completed");
        levelLabel.setText(game.getLevel().getLevelNumber());
        timeLabel.setText("Time: " + df.format(levelTime));

        playerPreferences.setPrefHighScore(game);

        if(playerPreferences.newHighScore()) {
            newHighScoreLabel.setText("New HS!");
        }
    }

    @Override
    public void buildStage() {
        // Create menu button
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 220);

        // Creating high score button, sending game through to high score list.
        LabelButton highScoreButton = new LabelButton("High Score");
        highScoreButton.setPosition(Config.UI_WIDTH/2 - highScoreButton.getWidth()/2, Config.UI_HEIGHT/2 - 300);
        highScoreButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.isMultiplayer()) {
                            ScreenManager.getInstance().showScreen(ScreenEnum.HIGHSCORE, game);
                        } else {
                            ScreenManager.getInstance().showScreen(ScreenEnum.HIGHSCORE, game);
                        }
                        return false;
                    }
                });

        // Add button to the stage
        this.ui.addActor(menuButton);
        this.ui.addActor(highScoreButton);

        font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        completedLabel = new Label("High Score", labelStyle);
        levelLabel = new Label("LevelID", labelStyle);
        timeLabel = new Label("Time", labelStyle);
        newHighScoreLabel = new Label("", labelStyle);
        table = new Table();
        //table.top().padLeft(Config.UI_WIDTH/2 - 100).left().padTop(10);
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(completedLabel);
        table.row();
        table.add(levelLabel);
        table.row();
        table.add(timeLabel).padTop(250);
        table.row();
        table.add(newHighScoreLabel).padTop(50);

        this.ui.addActor(table);
    }
}
