package com.mygdx.hastypastry.views;

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
    protected Label timeLabel;
    protected float levelTime;
    protected DecimalFormat df = new DecimalFormat("###.##");
    private Texture texture = new Texture("star.png");
    private Sprite sprite = new Sprite(texture);
    private float rotationDegree;
    protected Label newHighScoreLabel;
    protected PlayerPreferences playerPreferences;

    public HighScoreListView(Game game) {
        super();
        this.game = game;
        sprite.setSize(Config.UI_WIDTH/80, Config.UI_WIDTH/80);
        // sprite.setOrigin(Config.UI_WIDTH/2 - sprite.getWidth()/2, Config.UI_HEIGHT/3);
        sprite.setOrigin(sprite.getWidth()/2,-15);
        playerPreferences = new PlayerPreferences();
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        rotationDegree = 15.0f;
        for(float ranking : game.getLevel().getStarValues()) {
            if(levelTime < ranking) {
                sprite.draw(batch);
                sprite.setPosition(Config.WORLD_WIDTH/2 - sprite.getWidth()/2, Config.WORLD_HEIGHT/2);
                sprite.setRotation(rotationDegree);
                rotationDegree -= 15.0f;
            }
        }
        batch.end();

        levelTime = (float) game.getPlayer().getNewLevelTime().get(game.getPlayer().getNewLevelTime().size() - 1);
        highscoreLabel.setText("High Score");
        levelLabel.setText(game.getLevel().getLevelID());
        timeLabel.setText("Time: " + df.format(levelTime));

        playerPreferences.setPrefHighScore(game);

        if(playerPreferences.newHighScore()) {
            newHighScoreLabel.setText("New HS!");
        }
        System.out.println(playerPreferences.newHighScore());
        System.out.println((playerPreferences.isHighScoreSet(game)));
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
        timeLabel = new Label("Time", labelStyle);
        newHighScoreLabel = new Label("", labelStyle);
        table = new Table();
        //table.top().padLeft(Config.UI_WIDTH/2 - 100).left().padTop(10);
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(highscoreLabel).center();
        table.row();
        table.add(levelLabel).center();
        table.row();
        table.add(timeLabel).padTop(250);
        table.row();
        table.add(newHighScoreLabel).padTop(50);

        this.ui.addActor(table);

    }
}
