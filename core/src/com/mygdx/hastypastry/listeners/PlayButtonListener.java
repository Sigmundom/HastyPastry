package com.mygdx.hastypastry.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.PlayerPreferences;

public class PlayButtonListener extends InputListener {
    private Game game;
    private PlayerPreferences playerPreferences;

    public PlayButtonListener(Game game, PlayerPreferences playerPreferences) {
        this.game = game;
        this.playerPreferences = playerPreferences;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if(playerPreferences.isSoundEffectsEnabled()) {
            MusicAndSound.instance.getButtonSound().play(0.5f);
        }
        game.ready();
    }
}
