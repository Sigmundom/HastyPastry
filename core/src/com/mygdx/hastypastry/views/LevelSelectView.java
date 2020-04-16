package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.ui.MenuButton;

public class LevelSelectView extends BaseView {
    private BitmapFont font = new BitmapFont();
    private float levelHighScore;
    private int numLevelStars;
    private String levelStars;
    private PlayerPreferences playerPreferences;

    public LevelSelectView() {
        super();
        levelHighScore = 0;
        numLevelStars = 0;
        playerPreferences = new PlayerPreferences();
    }

    @Override
    public void buildStage() {
        // Find number of levels
        int numberOfLevels = Level.getNumberOfLevels();

        // Create inner table
        Table table = new Table();

        // Add padding
        table.padTop(10);
        table.setFillParent(false);
        table.setSize(Config.UI_WIDTH, Config.UI_HEIGHT - 50);

        // Set alignment
        table.top();

        // Add levels to the columns
        for (int j = 0; j < numberOfLevels; j++) {
            numLevelStars = 0;
            levelHighScore = playerPreferences.getLevelHighScore("Level " + String.valueOf(j + 1));
            for(float ranking : new Level("Level " + String.valueOf(j + 1)).getStarRank()) {
                if((levelHighScore != 0) && (levelHighScore < ranking)) {
                    numLevelStars++;
                }
            }

            switch(numLevelStars) {
                case 0:
                    levelStars = "";
                    break;
                case 1:
                    levelStars = "*";
                    break;
                case 2:
                    levelStars = "* *";
                    break;
                case 3:
                    levelStars = "* * *";
                    break;
            }

            MenuButton button = new MenuButton(String.valueOf(j + 1) + "\n" + levelStars,
                    ScreenEnum.DRAW, new Game(new Level("Level " + String.valueOf(j + 1))));
            table.add(button).growX().pad(10);
            int m = j % 3;
            if (m == 2) {
                table.row();
            }
        }

        // Add menu-button
        MenuButton menuButton = new MenuButton("     Main menu     ", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, 15);

        // Add table to stage
        this.ui.addActor(table);

        this.ui.addActor(menuButton);
        // Add levels to table
    }

    @Override
    public void draw(SpriteBatch batch, float delta){
        // Set font parameters
        font.setColor(Color.BLACK);
        font.getData().setScale(0.1f);
        font.setUseIntegerPositions(false);

        // Find width of text for centering
        String text = "Select a level";
        GlyphLayout gl1 = new GlyphLayout();
        gl1.setText(font, text);

        // Draw the text
        font.draw(batch, text, Config.WORLD_WIDTH/2 - gl1.width/2, Config.WORLD_HEIGHT - gl1.height);
    }
}
