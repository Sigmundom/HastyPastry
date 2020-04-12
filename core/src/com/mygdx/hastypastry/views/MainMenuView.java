package com.mygdx.hastypastry.views;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.MusicAndSound;
import com.mygdx.hastypastry.ui.ExitButton;
import com.mygdx.hastypastry.ui.MenuButton;

public class MainMenuView extends BaseView {
    protected Texture background = new Texture("bg_menu.png");
    private MenuButton singlePlayerBtn;
    private MenuButton multiPlayerBtn;
    private MenuButton preferenceButton;
    private Music menuMusic;
    private ExitButton exitButton;
    private PlayerPreferences playerPreferences;
    private MusicAndSound musicAndSound = new MusicAndSound();

    public MainMenuView(PlayerPreferences playerPreferences) {
        super();
        this.playerPreferences = playerPreferences;
        menuMusic = musicAndSound.getMenuMusic();
    }

    public MainMenuView(Music menuMusic, PlayerPreferences playerPreferences) {
        super();
        this.playerPreferences = playerPreferences;
        this.menuMusic = menuMusic;
        this.menuMusic.setVolume(0.7f);
    }

    @Override
    public void buildStage() {
        // Add sound to main menu.
        if(playerPreferences.isMusicEnabled()) {
            if(!menuMusic.isPlaying()) {
                menuMusic.setLooping(true);
                menuMusic.setVolume(0.7f);
                menuMusic.play();
            }
        }

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
        singlePlayerBtn = new MenuButton("Single Player", ScreenEnum.LEVELSELECT, menuMusic);
        multiPlayerBtn = new MenuButton("Multiplayer", ScreenEnum.LOGIN, menuMusic);
        preferenceButton = new MenuButton("Settings", ScreenEnum.PREFERENCES, menuMusic, playerPreferences);
        exitButton = new ExitButton("Exit Game");

        //Add buttons to table
        mainTable.add(singlePlayerBtn).growX().pad(25);
        mainTable.row();
        mainTable.add(multiPlayerBtn).growX().pad(15);
        mainTable.row();
        mainTable.add(preferenceButton).growX().pad(20);
        mainTable.row();
        mainTable.add(exitButton).growX().pad(25);

        // Add table to stage
        this.ui.addActor(mainTable);


    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        batch.draw(this.background, 0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        batch.end();
    }
}
