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

public class PreferenceView extends BaseView {
    protected Texture background = new Texture("bg_menu.png");

    private PlayerPreferences playerPreferences;
    private SettingsCheckBox soundEffectCheckBox;

    private BitmapFont font;
    private MenuButton menuButton;
    private Label settingsLabel;
    private Music menuMusic;

    public PreferenceView(Music menuMusic) {
        super();
        playerPreferences = new PlayerPreferences();
        this.menuMusic = menuMusic;
    }

    @Override
    public void buildStage() {

        if(!menuMusic.isPlaying()) {
            menuMusic.play();
        }

        soundEffectCheckBox = new SettingsCheckBox();
        soundEffectCheckBox.setPosition(Config.UI_WIDTH/2 - soundEffectCheckBox.getWidth()/2,Config.UI_HEIGHT/2);
        soundEffectCheckBox.setChecked(true);
        soundEffectCheckBox.setChecked( playerPreferences.isSoundEffectsEnabled());
        soundEffectCheckBox.addListener(
                new InputListener() {
                    @Override
                    public boolean handle(Event event) {
                        boolean enabled = soundEffectCheckBox.isChecked();
                        playerPreferences.setSoundEffectsEnabled(enabled);
                        return false;
                    }
                });

        font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        settingsLabel = new Label("Settings", labelStyle);

        Table table = new Table();

        table.setFillParent(true);

        table.top().padTop(30);
        table.setFillParent(true);
        table.add(settingsLabel);

        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU_RESET, menuMusic);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 280);
        this.ui.addActor(menuButton);
        this.ui.addActor(table);

    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        batch.draw(this.background, 0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        batch.end();

        settingsLabel.setText("Settings");
    }


}
