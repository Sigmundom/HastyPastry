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
    // protected Game game;
    protected Preferences preferences;
    protected boolean newChange;

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "playerprfs";

    private static final String PREF_LEVEL1_HS = "Level1";
    //private static final String PREF_LEVEL2_HS = "Level2";
    //private static final String PREF_LEVEL3_HS = "Level3";

    public PlayerPreferences() {
        preferences = getPrefs().putFloat(PREF_LEVEL1_HS, 0.0f);
        //preferences = getPrefs().putFloat(PREF_LEVEL2_HS, 0.0f);
        //preferences = getPrefs().putFloat(PREF_LEVEL3_HS, 0.0f);
    }

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public void setPrefHighScore(Game game) {
        newChange = false;
        if((getPrefs().getFloat(game.getLevel().getLevelID()) == 0.0f) ||
                (getPrefs().getFloat(game.getLevel().getLevelID()) <
                        (float) game.getPlayer().getNewLevelTime().get(game.getPlayer().getNewLevelTime().size() - 1))) {
            getPrefs().putFloat(game.getLevel().getLevelID(), (float) game.getPlayer().getNewLevelTime().get(game.getPlayer().getNewLevelTime().size() - 1));
            newChange = true;
        }
    }

    public float isHighScoreSet(Game game) {
        return getPrefs().getFloat(game.getLevel().getLevelID());
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

    public boolean newHighScore() { return newChange; }
}
