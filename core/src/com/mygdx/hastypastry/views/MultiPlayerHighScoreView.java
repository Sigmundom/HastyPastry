package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.ui.MenuButton;

import java.text.DecimalFormat;

public class MultiPlayerHighScoreView extends BaseView {
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
    protected Label newHighScoreLabel;
    protected PlayerPreferences playerPreferences;

    public MultiPlayerHighScoreView(Game game) {
        super();
        this.game = game;
        playerPreferences = new PlayerPreferences();
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        for(float ranking : game.getLevel().getStarRank()) {
            // ingen stjerner her
        }
        batch.end();

        highscoreLabel.setText("High Scores");
        levelLabel.setText(game.getLevel().getLevel());

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
