package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.Assets;

public class ExitButton extends TextButton {
    private PlayerPreferences playerPreferences = new PlayerPreferences();

    public ExitButton(String text /*, final ScreenEnum navigateTo, final Object... params*/) {
        super(text , Assets.instance.getManager().get(Assets.orangeUiSkin), "default");
        this.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if(playerPreferences.isSoundEffectsEnabled()) {
                            MusicAndSound.instance.getButtonSound().play(0.5f);
                        }
                        System.exit(0);
                        return false;
                    }
                }
        );
    }
}
