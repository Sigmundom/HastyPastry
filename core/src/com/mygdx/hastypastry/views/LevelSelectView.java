package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.Level;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.ui.MenuButton;

public class LevelSelectView extends BaseView {
    private BitmapFont font = new BitmapFont();
    private float levelHighScore;
    private int numLevelStars;
    private String levelStars;
    private Label selectLabel;

    public LevelSelectView() {
        super();
        levelHighScore = 0;
        numLevelStars = 0;
    }

    @Override
    public void buildStage() {
        // Generate table for title.
        Table titleTable = new Table();
        titleTable.top().padTop(30);
        titleTable.setFillParent(true);

        // Generating font.
        font = generateFont("pixelfont.TTF", 28);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        selectLabel = new Label("Select a level", labelStyle);
        titleTable.add(selectLabel);

        // Find number of levels
        int numberOfLevels = Level.getNumberOfLevels();

        // Create inner table
        Table table = new Table();

        // Add padding
        table.padTop(25);
        table.setFillParent(false);
        table.setSize(Config.UI_WIDTH, Config.UI_HEIGHT - 50);

        // Set alignment
        table.top();

        // Add levels to the columns
        for (int j = 0; j < numberOfLevels; j++) {
            numLevelStars = 0;
            levelHighScore = PlayerPreferences.instance.getLevelHighScore("Level " + String.valueOf(j + 1));
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
        this.ui.addActor(titleTable);
        this.ui.addActor(table);

        this.ui.addActor(menuButton);
        // Add levels to table
    }

    @Override
    public void draw(SpriteBatch batch, float delta){
        // Set font parameters
        selectLabel.setText("Select a level");
    }
}
