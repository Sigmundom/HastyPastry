package com.mygdx.hastypastry.views;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.SettingsCheckBox;
import com.mygdx.hastypastry.ui.SettingsSlider;

public class PreferenceView extends BaseView {
    protected Texture background = new Texture("bg_menu.png");

    private SettingsCheckBox soundEffectCheckBox;
    private SettingsCheckBox musicCheckBox;
    private SettingsSlider musicVolumeSlider;

    private BitmapFont titleFont, contentFont;
    private MenuButton menuButton;
    private Label settingsLabel;
    private Label soundOnOffLabel;
    private Label musicOnOffLabel;
    private Label musicVolumeLabel;
    private PlayerPreferences playerPreferences = new PlayerPreferences();
    private Sound buttonSound;
    private Music gameMusic;

    public PreferenceView() {
        super();
        buttonSound = MusicAndSound.instance.getButtonSound();
        gameMusic = MusicAndSound.instance.getGameMusic();
    }

    @Override
    public void buildStage() {

        /*if(playerPreferences.isMusicEnabled()) {
            if (!menuMusic.isPlaying()) {
                menuMusic.setLooping(true);
                menuMusic.play();
            }
        }*/

        soundEffectCheckBox = new SettingsCheckBox();
        soundEffectCheckBox.setChecked(playerPreferences.isSoundEffectsEnabled());
        soundEffectCheckBox.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        if(playerPreferences.isSoundEffectsEnabled()) {
                            buttonSound.play(0.5f);
                        }
                        boolean enabled = soundEffectCheckBox.isChecked();
                        playerPreferences.setSoundEffectsEnabled(enabled);
                        return false;
                    }
                });

        musicCheckBox = new SettingsCheckBox();
        musicCheckBox.setChecked((playerPreferences.isMusicEnabled()));
        musicCheckBox.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        if(playerPreferences.isSoundEffectsEnabled()) {
                            buttonSound.play(0.5f);
                        }
                        boolean enabled = musicCheckBox.isChecked();
                        playerPreferences.setMusicEnabled(enabled);
                        if(enabled) {
                            gameMusic.setVolume(playerPreferences.getMusicVolume());
                            gameMusic.play();
                        }
                        else {
                            gameMusic.stop();
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
                        gameMusic.setVolume(musicVolumeSlider.getValue());
                        return false;
                    }
                });

        titleFont = generateFont("pixelfont.TTF", 32);
        contentFont = generateFont("pixelfont.TTF", 18);
        Label.LabelStyle titleLabelStyle = new Label.LabelStyle(titleFont, Color.BLACK);
        Label.LabelStyle contentLabelStyle = new Label.LabelStyle(contentFont, Color.BLACK);

        settingsLabel = new Label("Settings", titleLabelStyle);
        soundOnOffLabel = new Label("Sound on/off", contentLabelStyle);
        musicOnOffLabel = new Label("Music on/off", contentLabelStyle);
        musicVolumeLabel = new Label("Music volume", contentLabelStyle);

        Table table = new Table();

        table.top().padTop(30).padLeft(50).padRight(50);
        table.setFillParent(true);
        table.add(settingsLabel).padLeft(50);
        table.row();
        table.add(soundOnOffLabel).left().padTop(50);
        table.add(soundEffectCheckBox).width(Config.UI_WIDTH/6f).right().padTop(50);
        table.row();
        table.add(musicOnOffLabel).left().padTop(50);
        table.add(musicCheckBox).width(Config.UI_WIDTH/6f).right().padTop(60);
        table.row();
        table.add(musicVolumeLabel).left().padTop(50);
        table.row();
        table.add(musicVolumeSlider).center().padTop(20).fillX();


        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU_RESET);
        menuButton.setWidth(Config.UI_WIDTH-160);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 280);
        this.ui.addActor(menuButton);
        this.ui.addActor(table);

    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        batch.draw(this.background, 0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        batch.end();

        settingsLabel.setText("Settings");
        soundOnOffLabel.setText("Sound on/off");
        musicOnOffLabel.setText("Music on/off");
        musicVolumeLabel.setText("Music volume");
    }


}
