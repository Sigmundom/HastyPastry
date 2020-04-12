package com.mygdx.hastypastry.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicAndSound {
    private Music menuMusic;

    public MusicAndSound() {
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("WorldTheme.mp3"));;
    }

    public Music getMenuMusic() { return menuMusic; }
}
