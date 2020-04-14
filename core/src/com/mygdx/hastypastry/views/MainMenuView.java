package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.ExitButton;
import com.mygdx.hastypastry.ui.MenuButton;

public class MainMenuView extends BaseView {
    protected Texture background = new Texture("bg_menu.png");
    private MenuButton singlePlayerBtn;
    private MenuButton multiPlayerBtn;
    private MenuButton preferenceButton;
    //private Music menuMusic;
    private ExitButton exitButton;
    private PlayerPreferences playerPreferences = new PlayerPreferences();

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
        singlePlayerBtn = new MenuButton("Single Player", ScreenEnum.LEVELSELECT);
        multiPlayerBtn = new MenuButton("Multiplayer", ScreenEnum.LOGIN);
        preferenceButton = new MenuButton("Settings", ScreenEnum.PREFERENCES, playerPreferences);
        exitButton = new ExitButton("Exit Game");

        //Add buttons to table
        mainTable.add(singlePlayerBtn).growX().pad(25).padTop(5);
        mainTable.row();
        mainTable.add(multiPlayerBtn).growX().pad(25).padTop(5);
        mainTable.row();
        mainTable.add(preferenceButton).growX().pad(25).padTop(5);
        mainTable.row();
        mainTable.add(exitButton).growX().pad(25).padTop(5);

        // Add table to stage
        this.ui.addActor(mainTable);


    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        batch.draw(this.background, 0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        batch.end();
    }
}
