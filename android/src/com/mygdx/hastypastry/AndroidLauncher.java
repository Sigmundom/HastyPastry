package com.mygdx.hastypastry;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * Runs at android startup, initialing Database-object. Does not check for network-communication
 * Makes game object.
 */
public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		initialize(new HastyPastryGame(new FBDatabase()), config);
	}
}
