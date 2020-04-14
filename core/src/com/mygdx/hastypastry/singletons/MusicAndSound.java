package com.mygdx.hastypastry.singletons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MusicAndSound {
    public static final MusicAndSound instance = new MusicAndSound();
    private Music menuMusic;
    private Sound buttonSound;

    private MusicAndSound() {
    }

    public Music getGameMusic() { return menuMusic; }

    public Sound getButtonSound() { return buttonSound; }

    public void init() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("8-punk.mp3"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button_click.ogg"));
        buttonSound.setVolume(buttonSound.play(), 0.5f);
    }
}
