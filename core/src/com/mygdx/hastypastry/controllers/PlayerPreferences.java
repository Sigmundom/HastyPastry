package com.mygdx.hastypastry.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.hastypastry.models.Game;

/** Class for setting and updating user preferences. This info is
 * stored in Android's SharedPreferences, meaning that user settings
 * and high scores survive app updates, but are deleted when uninstalling.
 *
 @author Hanna*/

public class PlayerPreferences {
    protected boolean newChange;
    protected Preferences preferences;
    protected float personalHighScore;

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "playerprfs";

    public PlayerPreferences() {
    }

    // Should work for both checkboxes, sliders and high score storage.
    protected Preferences getPrefs() {
        if(preferences == null) {
            preferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return preferences;
    }

    public void setPrefHighScore(Game game) {
        newChange = false;
        if(getPrefs().getFloat(game.getLevel().getLevelNumber()) == 0.0f ||
                getPrefs().getFloat(game.getLevel().getLevelNumber()) > game.getPlayer().getNewLevelTime()) {
            getPrefs().putFloat(game.getLevel().getLevelNumber(), game.getPlayer().getNewLevelTime()).flush();
            newChange = true;
        }
        personalHighScore = getPrefs().getFloat(game.getLevel().getLevelNumber());
    }

    public float isHighScoreSet(Game game) {
        return getPrefs().getFloat(game.getLevel().getLevelNumber());
    }


    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
    }

    public boolean newHighScore() { return newChange; }

    public float getPersonalHighScore() {
        return personalHighScore;
    }

}
