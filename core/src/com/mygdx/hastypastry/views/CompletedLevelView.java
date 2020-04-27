package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.listeners.MyButtonListener;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.StyledTextButton;
import com.mygdx.hastypastry.ui.MenuButton;

import java.text.DecimalFormat;

public class CompletedLevelView extends AbstractView {
    private Game game;
    protected BitmapFont font;
    private Label completedLabel;
    private Label levelLabel;
    private Label timeLabel;
    private float levelTime;
    private DecimalFormat df = new DecimalFormat("###.##");
    private Texture texture = new Texture("star.png");
    private Sprite sprite = new Sprite(texture);
    private float rotationDegree;
    private Label newHighScoreLabel;


    public CompletedLevelView(Game game) {
        super();
        this.game = game;
        sprite.setSize(Config.UI_WIDTH/80, Config.UI_WIDTH/80);
        sprite.setOrigin(sprite.getWidth()/2,-15);
    }

    /**
     * Draws the number of stars the user has on a level according to the
     * specified star rankings for said level.
     * Also sets text for specified labels.
     *
     * @param batch Spritebatch to draw new models/sprites.
     * @param delta The number of seconds that have passed since the last frame.
     */
    @Override
    public void draw(SpriteBatch batch, float delta){
        levelTime = game.getPlayer().getNewLevelTime();
        System.out.println("new level time is: " + game.getPlayer().getNewLevelTime());
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

        completedLabel.setText("Completed");
        levelLabel.setText(game.getLevel().getLevel());
        timeLabel.setText("Time: " + df.format(levelTime));

        PlayerPreferences.instance.setPrefHighScore(game);

        if(PlayerPreferences.instance.newHighScore()) {
            newHighScoreLabel.setText("New HS!");
        }
    }

    @Override
    public void buildStage() {
        // Sound effects
        if(PlayerPreferences.instance.isMusicEnabled()) {
            MusicAndSound.instance.getGameMusic().setVolume(PlayerPreferences.instance.getMusicVolume());
        }

        // Create menu button
        MenuButton menuButton = new MenuButton("  Menu  ", ScreenEnum.MAIN_MENU);

        // Create replay-button
        MenuButton replayButton = new MenuButton("  Replay level  ", ScreenEnum.DRAW, new Game(new Level(game.getLevel().getLevel())));

        // Set width and position of menu button
        menuButton.setWidth(replayButton.getWidth());
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2 - 90, Config.UI_HEIGHT/2 - 300);

        // Check current level
        String nextLevel = getNextLevel(game.getLevel().getLevel());
        if (nextLevel != null) {
            replayButton.setPosition(Config.UI_WIDTH/2 - replayButton.getWidth()/2 - 90, Config.UI_HEIGHT/2 - 220);

            MenuButton nextLevelButton = new MenuButton("  Next Level  ", ScreenEnum.DRAW, new Game(new Level(nextLevel)));
            nextLevelButton.setWidth(replayButton.getWidth());
            nextLevelButton.setPosition(Config.UI_WIDTH/2 - nextLevelButton.getWidth()/2 + 90, Config.UI_HEIGHT/2 - 220);

            this.ui.addActor(nextLevelButton);
        } else {
            replayButton.setPosition(Config.UI_WIDTH/2 - replayButton.getWidth()/2, Config.UI_HEIGHT/2 - 220);
        }

        // Creating high score button, sending game through to high score list.
        StyledTextButton highScoreButton = new StyledTextButton("  High Score  ");

        highScoreButton.setWidth(replayButton.getWidth());
        highScoreButton.setPosition(Config.UI_WIDTH/2 - highScoreButton.getWidth()/2 + 90, Config.UI_HEIGHT/2 - 300);
        highScoreButton.addListener(new MyButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ScreenManager.getInstance().showScreen(ScreenEnum.HIGHSCORE, game);
            }
        });

        // Add buttons to the stage
        this.ui.addActor(menuButton);
        this.ui.addActor(replayButton);
        this.ui.addActor(highScoreButton);

        font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        completedLabel = new Label("High Score", labelStyle);
        levelLabel = new Label("LevelID", labelStyle);
        timeLabel = new Label("Time", labelStyle);
        newHighScoreLabel = new Label("", labelStyle);
        Table table = new Table();
        //table.top().padLeft(Config.UI_WIDTH/2 - 100).left().padTop(10);
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(completedLabel);
        table.row();
        table.add(levelLabel);
        table.row();
        table.add(timeLabel).padTop(90);
        table.row();
        table.add(newHighScoreLabel).padTop(180);

        this.ui.addActor(table);
    }

    private String getNextLevel(String levelNumber) {
        String[] string = levelNumber.trim().split("\\s+");
        int num = Integer.parseInt(string[1]);
        if (num != Level.getNumberOfLevels()) {
            return "Level " + (num + 1);
        }else {
            return null;
        }
        // Legger til sjekk for å se om getNextLevel gir null
    }
}
