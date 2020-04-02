package com.mygdx.hastypastry.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.HastyPastryGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Config.UI_HEIGHT;
		config.width = Config.UI_WIDTH;
		config.foregroundFPS = 60;
		new LwjglApplication(new HastyPastryGame(new DesktopDatabase()), config);
	}
}
