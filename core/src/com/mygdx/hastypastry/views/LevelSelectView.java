package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.FileReader;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.ui.MenuButton;

import java.util.ArrayList;

public class LevelSelectView extends BaseView {

    private BitmapFont font = new BitmapFont();

    public LevelSelectView() { super(); }

    @Override
    public void buildStage() {
        // Find all levels
        FileReader fileReader = new FileReader();
        ArrayList<String> fileData = fileReader.getInternalFileData("levels.txt");

        // Find number of levels
        int i = 0;
        for (String line : fileData){
            if (line.contains("Level")) {
                i++;
            }
        }

        // Create inner table
        Table table = new Table();

        // Add padding
        table.padTop(10);
        table.setFillParent(false);
        table.setSize(Config.UI_WIDTH, Config.UI_HEIGHT - 50);

        // Set alignment
        table.top();

        // Add levels to the columns
        for (int j = 0; j < i; j++) {
            MenuButton button = new MenuButton(String.valueOf(j + 1), ScreenEnum.DRAW, new Game(new Level("Level " + String.valueOf(j + 1))));
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
        /*
        for (int j = 0; j < i; j++){
            MenuButton button = new MenuButton("  " + String.valueOf(j + 1) + "  ", ScreenEnum.DRAW, new Game(new Level("Level " + String.valueOf(j + 1))));
            table.add(button).pad(10);
        }
         */
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
