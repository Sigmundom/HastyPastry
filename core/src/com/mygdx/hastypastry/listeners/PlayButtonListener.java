package com.mygdx.hastypastry.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.PlayerPreferences;

public class PlayButtonListener extends InputListener {
    private Game game;

    public PlayButtonListener(Game game) {
        this.game = game;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
            MusicAndSound.instance.getButtonSound().play(0.5f);
        }
        game.ready();
    }
}
