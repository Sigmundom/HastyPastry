package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class MenuButton extends TextButton {

    public MenuButton(String text, final ScreenEnum navigateTo, final Object... params) {
        super(text, Assets.instance.getManager().get(Assets.orangeUiSkin), "default");
        this.addListener(
            new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if(PlayerPreferences.instance.isMusicEnabled()) {
                        MusicAndSound.instance.getGameMusic().setVolume(PlayerPreferences.instance.getMusicVolume());
                    }
                    if(PlayerPreferences.instance.isSoundEffectsEnabled()) {
                        MusicAndSound.instance.getButtonSound().play(0.5f);
                    }
                    if(navigateTo == ScreenEnum.MAIN_MENU) {
                        DBManager.instance.getDB().exitLobby();
                    }
                    ScreenManager.getInstance().showScreen(navigateTo, params);
                }
            }
        );
    }
}
