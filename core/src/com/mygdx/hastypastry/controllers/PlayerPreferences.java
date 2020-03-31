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
    protected float personalHighScore;

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "playerprfs";

    private static final String PREF_LEVELA_HS = "Level A";
    //private static final String PREF_LEVELB_HS = "Level B";
    //private static final String PREF_LEVELC_HS = "Level C";

    public PlayerPreferences() {
        preferences = getPrefs().putFloat(PREF_LEVELA_HS, 0.0f);
        //preferences = getPrefs().putFloat(PREF_LEVELB_HS, 0.0f);
        //preferences = getPrefs().putFloat(PREF_LEVELC_HS, 0.0f);
    }

    protected Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public void setPrefHighScore(Game game) {
        System.out.println("Current HS: " + getPrefs().getFloat(PREF_LEVELA_HS));
        System.out.println(PREF_LEVELA_HS == game.getLevel().getLevelNumber());
        System.out.println("Game HS: " + game.getPlayer().getNewLevelTime());
        newChange = false;
        if(getPrefs().getFloat(game.getLevel().getLevelNumber()) == 0.0f ||
                getPrefs().getFloat(game.getLevel().getLevelNumber()) > game.getPlayer().getNewLevelTime()) {
            System.out.println("du kommer deg hit..");
            getPrefs().putFloat(game.getLevel().getLevelNumber(), game.getPlayer().getNewLevelTime()).flush();
            newChange = true;
        }
        personalHighScore = getPrefs().getFloat(game.getLevel().getLevelNumber());
        // System.out.println("personal HS: " + personalHighScore);
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

    public boolean newHighScore() { return newChange; }

    public float getPersonalHighScore() {
        return personalHighScore;
    }
}
