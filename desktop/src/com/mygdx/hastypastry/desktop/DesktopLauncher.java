package com.mygdx.hastypastry.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.HastyPastryGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Config.HEIGHT;
		config.width = Config.WIDTH;
		new LwjglApplication(new HastyPastryGame(), config);
	}
}
