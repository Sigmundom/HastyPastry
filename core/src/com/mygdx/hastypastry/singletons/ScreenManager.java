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
    public void initialize(Game game) {
        this.game = game;
        //Tell the manager to start loading
        Assets.load();
//Tell the program to "loop" the loading until finished. Essentially stopping the game from continuing.
        Assets.manager.finishLoading();
    }

    // Show in the game the screen which enum type is received
    public void showScreen(ScreenEnum screenEnum, Object... params) {

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        BaseView newScreen = screenEnum.getScreen(params);
        newScreen.buildStage();
        game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
