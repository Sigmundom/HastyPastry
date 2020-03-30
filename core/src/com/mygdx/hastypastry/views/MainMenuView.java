package com.mygdx.hastypastry.views;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.ui.MenuButton;

public class MainMenuView extends BaseView {

    private MenuButton singlePlayerBtn;
    private MenuButton multiPlayerBtn;

    public MainMenuView() {
        super();
    }

    @Override
    public void buildStage() {
        //Create Table
        Table mainTable = new Table();

        //Set table to fill stage
        mainTable.setFillParent(true);

        //Set padding
        mainTable.padTop(100);
        mainTable.padLeft(50);
        mainTable.padRight(50);

        //Set alignment of contents in the table.
        mainTable.top();

        //Create Buttons
        singlePlayerBtn = new MenuButton("Single Player", ScreenEnum.DRAW, new Game(new Level("Level 1")));
        multiPlayerBtn = new MenuButton("Multiplayer", ScreenEnum.LOBBY, "Per");

        //Add buttons to table
        mainTable.add(singlePlayerBtn).growX().pad(25);
        mainTable.row();
        mainTable.add(multiPlayerBtn).growX().pad(25);

//        Add table to stage
        this.ui.addActor(mainTable);
    }
}
