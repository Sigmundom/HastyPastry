package com.mygdx.hastypastry.views;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.SettingsCheckBox;
import com.mygdx.hastypastry.ui.SettingsSlider;

public class PreferenceView extends BaseView {
    protected Texture background = new Texture("bg_menu.png");

    private SettingsCheckBox soundEffectCheckBox;
    private SettingsSlider soundVolumeSlider;
    private SettingsCheckBox musicCheckBox;
    private SettingsSlider musicVolumeSlider;

    private BitmapFont titleFont, contentFont;
    private MenuButton menuButton;
    private Label settingsLabel;
    private Label soundOnOffLabel;
    private Label soundVolumeLabel;
    private Label musicOnOffLabel;
    private Label musicVolumeLabel;
    private PlayerPreferences playerPreferences = new PlayerPreferences();
    private Music menuMusic;

    public PreferenceView(Music menuMusic) {
        super();
        this.menuMusic = menuMusic;
    }

    @Override
    public void buildStage() {

        if(playerPreferences.isMusicEnabled()) {
            if (!menuMusic.isPlaying()) {
                menuMusic.play();
            }
        }

        soundEffectCheckBox = new SettingsCheckBox();
        soundEffectCheckBox.setChecked(playerPreferences.isSoundEffectsEnabled());
        soundEffectCheckBox.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        boolean enabled = soundEffectCheckBox.isChecked();
                        playerPreferences.setSoundEffectsEnabled(enabled);
                        return false;
                    }
                });

        soundVolumeSlider = new SettingsSlider();
        soundVolumeSlider.setValue(playerPreferences.getSoundVolume());
        soundVolumeSlider.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        playerPreferences.setSoundVolume(soundVolumeSlider.getValue());
                        //menuMusic.setVolume(musicVolumeSlider.getValue());
                        return false;
                    }
                });

        musicCheckBox = new SettingsCheckBox();
        musicCheckBox.setChecked((playerPreferences.isMusicEnabled()));
        musicCheckBox.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        boolean enabled = musicCheckBox.isChecked();
                        playerPreferences.setMusicEnabled(enabled);
                        if(enabled) {
                            menuMusic.play();
                        }
                        else {
                            menuMusic.stop();
                        }
                        return false;
                    }
                });

        musicVolumeSlider = new SettingsSlider();
        musicVolumeSlider.setValue(playerPreferences.getMusicVolume());
        musicVolumeSlider.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        playerPreferences.setMusicVolume(musicVolumeSlider.getValue());
                        menuMusic.setVolume(musicVolumeSlider.getValue());
                        return false;
                    }
                });

        titleFont = generateFont("pixelfont.TTF", 32);
        contentFont = generateFont("pixelfont.TTF", 18);
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle(titleFont, Color.BLACK);
        Label.LabelStyle contentLabelStyle = new Label.LabelStyle(contentFont, Color.BLACK);

        settingsLabel = new Label("Settings", titleLabelStyle);
        soundOnOffLabel = new Label("Sound on/off", contentLabelStyle);
        soundVolumeLabel = new Label("Sound volume", contentLabelStyle);
        musicOnOffLabel = new Label("Music on/off", contentLabelStyle);
        musicVolumeLabel = new Label("Music volume", contentLabelStyle);

        Table table = new Table();

        table.top().padTop(30);
        table.setFillParent(true);
        table.add(settingsLabel).padLeft(20);
        table.row();
        table.add(soundOnOffLabel).left().padTop(50);
        table.add(soundEffectCheckBox).width(Config.UI_WIDTH/6f).right().padTop(50);
        table.row();
        table.add(soundVolumeLabel).left().padTop(30);
        table.row();
        table.add(soundVolumeSlider).center().padTop(10);
        table.row();
        table.add(musicOnOffLabel).left().padTop(60);
        table.add(musicCheckBox).width(Config.UI_WIDTH/6f).right().padTop(60);
        table.row();
        table.add(musicVolumeLabel).left().padTop(30);
        table.row();
        table.add(musicVolumeSlider).center().padTop(10);


        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU_RESET, menuMusic);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 100);
        this.ui.addActor(menuButton);
        this.ui.addActor(table);

    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        batch.draw(this.background, 0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        batch.end();

        settingsLabel.setText("Settings");
        soundOnOffLabel.setText("Sound on/off");
        soundVolumeLabel.setText("Sound volume");
        musicOnOffLabel.setText("Music on/off");
        musicVolumeLabel.setText("Music volume");
    }


}
