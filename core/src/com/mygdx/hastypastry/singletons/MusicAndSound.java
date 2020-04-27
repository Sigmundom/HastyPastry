package com.mygdx.hastypastry.singletons;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Singleton class for generating game music and button sound.
 */

public class MusicAndSound {
    public static final MusicAndSound instance = new MusicAndSound();
    private Music menuMusic;
    private Sound buttonSound;

    //Singleton - prevents instantiation from other classes
    private MusicAndSound() {
    }

    public Music getGameMusic() { return menuMusic; }

    public Sound getButtonSound() { return buttonSound; }

    public void init() {
        menuMusic = Assets.instance.getManager().get(Assets.menuMusic);
        buttonSound = Assets.instance.getManager().get(Assets.buttonSound);
        buttonSound.setVolume(buttonSound.play(), 0.5f);
    }
}
