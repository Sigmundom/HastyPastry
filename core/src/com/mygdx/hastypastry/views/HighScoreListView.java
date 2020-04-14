package com.mygdx.hastypastry.views;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.ui.MenuButton;

import java.text.DecimalFormat;

public class HighScoreListView extends BaseView {
    private Game game;
    private MenuButton menuButton;
    protected BitmapFont font;
    private Table table;
    protected Label highscoreLabel;
    protected Label levelLabel;
    protected Label personalHighScoreLabel;
    protected Label timeLabel;
    protected float levelTime;
    protected DecimalFormat df = new DecimalFormat("###.##");
    private Texture texture = new Texture("star.png");
    private Sprite sprite = new Sprite(texture);
    private float rotationDegree;
    protected Label newHighScoreLabel;
    protected PlayerPreferences playerPreferences;

    public HighScoreListView(Game game, Music menuMusic) {
        super();
        this.game = game;
        sprite.setSize(Config.UI_WIDTH/80, Config.UI_WIDTH/80);
        sprite.setOrigin(sprite.getWidth()/2,-15);
        playerPreferences = new PlayerPreferences();
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
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

        highscoreLabel.setText("High Score");
        levelLabel.setText(game.getLevel().getLevelNumber());

        playerPreferences.setPrefHighScore(game);
        personalHighScoreLabel.setText("Best: " + df.format(playerPreferences.getPersonalHighScore()));
        levelTime = playerPreferences.getPersonalHighScore();
    }

    @Override
    public void buildStage() {
        // Creating menu button.
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 300);

        // Add button to the stage
        this.ui.addActor(menuButton);

        font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        highscoreLabel = new Label("High Score", labelStyle);
        levelLabel = new Label("LevelID", labelStyle);
        personalHighScoreLabel = new Label("Personal High Score", labelStyle);
        table = new Table();
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(highscoreLabel);
        table.row();
        table.add(levelLabel);
        table.row();
        table.add(personalHighScoreLabel).padTop(250);

        this.ui.addActor(table);

    }
}
