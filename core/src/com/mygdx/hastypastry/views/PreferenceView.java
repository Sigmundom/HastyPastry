package com.mygdx.hastypastry.views;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.SettingsCheckBox;
import com.mygdx.hastypastry.ui.SettingsSlider;

/**
 * Provides the user with UI for adjusting the sound and music in the game.
 */

public class PreferenceView extends AbstractView {
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
    private Sound buttonSound;
    private Music gameMusic;

    public PreferenceView() {
        super();
        buttonSound = MusicAndSound.instance.getButtonSound();
        gameMusic = MusicAndSound.instance.getGameMusic();
    }

    @Override
    public void buildStage() {

        soundEffectCheckBox = new SettingsCheckBox();
        soundEffectCheckBox.setChecked(PlayerPreferences.instance.isSoundEffectsEnabled());
        soundEffectCheckBox.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
                            buttonSound.play(0.5f);
                        }
                        boolean enabled = soundEffectCheckBox.isChecked();
                        PlayerPreferences.instance.setSoundEffectsEnabled(enabled);
                    }
                });
        musicCheckBox = new SettingsCheckBox();
        musicCheckBox.setChecked((PlayerPreferences.instance.isMusicEnabled()));
        musicCheckBox.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
                            buttonSound.play(0.5f);
                        }
                        boolean enabled = musicCheckBox.isChecked();
                        PlayerPreferences.instance.setMusicEnabled(enabled);
                        if(enabled) {
                            gameMusic.setVolume(PlayerPreferences.instance.getMusicVolume());
                            gameMusic.play();
                        }
                        else {
                            gameMusic.stop();
                        }
                    }
                });

        musicVolumeSlider = new SettingsSlider();
        musicVolumeSlider.setValue(PlayerPreferences.instance.getMusicVolume());
        musicVolumeSlider.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        PlayerPreferences.instance.setMusicVolume(musicVolumeSlider.getValue());
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
        table.add(musicOnOffLabel).left().padTop(30);
        table.add(musicCheckBox).width(Config.UI_WIDTH/6f).right().padTop(30);
        table.row();
        table.add(musicVolumeLabel).left().padTop(30);
        table.row();
        table.add(musicVolumeSlider).center().padTop(20).fillX();


        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
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
