package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.FileReader;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.ui.MenuButton;

import java.util.ArrayList;

public class LevelSelectView extends BaseView {

    private ArrayList<String> levels;

    public LevelSelectView() { super(); }

    @Override
    public void buildStage() {
        // Initialize ArrayList
        levels = new ArrayList<String>();

        // Find all levels
        FileReader fileReader = new FileReader();
        ArrayList<String> fileData = fileReader.getInternalFileData("levels.txt");

        // Find number of levels
        int i = 0;
        for (String line : fileData){
            if (line.contains("Level")){
                i++;
                levels.add("Level " + String.valueOf(i));
            }
        }

        // Create inner table
        Table innerTable = new Table();
        // table.setFillParent(true);
        innerTable.setSize(Config.UI_WIDTH, Config.UI_HEIGHT);

        // Add padding
        innerTable.padTop(10);

        // Set alignment
        innerTable.top();

        // Add levels to table
        for (String level : levels){
            MenuButton button = new MenuButton(level, ScreenEnum.DRAW, new Game(new Level(level)));
            innerTable.row();
            innerTable.add(button).growX().pad(25);
        }

        // Create ScrollPane
        ScrollPane pane = new ScrollPane(innerTable);
        // pane.setScrollingDisabled(false, true);
        pane.layout();
        pane.setFillParent(true);
        pane.setScrollingDisabled(true, false);
        pane.setForceScroll(false,true);
        pane.setFlickScroll(true);
        // pane.setSize(Config.UI_WIDTH, Config.UI_HEIGHT);

        // Create outer table
        Table outerTable = new Table();
        outerTable.setFillParent(true);

        outerTable.add(pane).fill().expand();

        // Add table to stage
        this.ui.addActor(outerTable);

        // Denne scrollet alt
        pane.setScrollPercentY(100);
        pane.act(Gdx.graphics.getDeltaTime());
        pane.updateVisualScroll();
    }
}
