package com.mygdx.hastypastry.listeners;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.PlayerPreferences;

public class MyButtonListener extends ClickListener {
//    @Override
//    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//        return true;
//    }
//
//    @Override
//    public boolean exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
//        return false;
//    }
//
//    @Override
//    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//        if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
//            MusicAndSound.instance.getButtonSound().play(0.5f);
//        }
//    }
    @Override
    public void clicked(InputEvent event, float x, float y) {
        if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
            MusicAndSound.instance.getButtonSound().play(0.5f);
        }
    }
}
