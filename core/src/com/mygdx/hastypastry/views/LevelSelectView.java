package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.controllers.FileReader;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.ui.MenuButton;

import java.util.ArrayList;

public class LevelSelectView extends BaseView {

    private ArrayList<String> levels;
    private Skin skin = new Skin(Gdx.files.internal("ui/uiskin.json"), new TextureAtlas("ui/uiskin.pack"));
    private ScrollPane scrollPane;

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

        // Create table
        Table table = new Table();
        table.setFillParent(true);

        // Add padding
        table.padTop(10);

        // Set alignment
        table.top();

        // Add levels to table
        for (String level : levels){
            MenuButton button = new MenuButton(level, ScreenEnum.DRAW, new Game(new Level(level)));
            table.row();
            table.add(button).growX().pad(25);
        }

        // Add table to stage
        this.ui.addActor(table);
    }
}
