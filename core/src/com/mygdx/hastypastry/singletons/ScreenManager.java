package com.mygdx.hastypastry.singletons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.views.BaseView;

public class ScreenManager {

    // Singleton: unique instance
    private static ScreenManager instance;

    // Reference to game
    private Game game;
    // Reference to asset manager
    private Assets assets;

    // Singleton: private constructor
    private ScreenManager() {
        super();
    }

    // Singleton: retrieve instance
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    // Initialization with the game class
    public void initialize(Game game, Assets assets) {
        this.game = game;
        this.assets = assets;

        //Tell the manager to start loading
        assets.load();
        //Tell the program to "loop" the loading until finished. Essentially stopping the game from continuing.
        assets.getManager().finishLoading();
    }

    // Show in the game the screen which enum type is received
    public void showScreen(ScreenEnum screenEnum, Object... params) {

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        BaseView newScreen = screenEnum.getScreen(assets, params);
        newScreen.buildStage();
        game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
