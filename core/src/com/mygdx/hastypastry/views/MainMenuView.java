package com.mygdx.hastypastry.views;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.ui.MenuButton;

public class MainMenuView extends BaseView {

    private MenuButton singlePlayerBtn;
    private MenuButton multiPlayerBtn;

    public MainMenuView(Assets assets) {
        super(assets);
    }

    @Override
    public void buildStage() {
        //Create Table
        Table mainTable = new Table();

        //Shows helping lines
        mainTable.debugTable();

        //Set table to fill stage
        mainTable.setFillParent(true);

        //Set padding
        mainTable.padTop(100);
        mainTable.padLeft(50);
        mainTable.padRight(50);

        //Set alignment of contents in the table.
        mainTable.top();

        //Create Buttons
        singlePlayerBtn = new MenuButton(assets, "Single Player", ScreenEnum.PLAY);
        multiPlayerBtn = new MenuButton(assets,"Multiplayer", ScreenEnum.LOBBY);

        //Add buttons to table
        mainTable.add(singlePlayerBtn).growX().pad(25);
        mainTable.row();
        mainTable.add(multiPlayerBtn).growX().pad(25);

//        Add table to stage
        this.ui.addActor(mainTable);
    }

}
