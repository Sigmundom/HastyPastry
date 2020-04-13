package com.mygdx.hastypastry.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class MusicAndSound {
    private Music menuMusic;
    private Sound buttonSound;

    public MusicAndSound() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("WorldTheme.mp3"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("button_click.ogg"));
    }

    public Music getMenuMusic() { return menuMusic; }

    public Sound getButtonSound() { return buttonSound; }
}
