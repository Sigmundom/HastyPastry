package com.mygdx.hastypastry.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.PlayerPreferences;

public class MyButtonListener extends ClickListener {
    @Override
    public void clicked(InputEvent event, float x, float y) {
        if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
            MusicAndSound.instance.getButtonSound().play(0.5f);
        }
    }
}
