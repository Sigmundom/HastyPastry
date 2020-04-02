package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.singletons.Assets;

public class HighScoreButton extends TextButton {
    public HighScoreButton(String text/*, final ScreenEnum navigateTo, final Object... params*/) {
        super(text, Assets.instance.getManager().get(Assets.uiSkin), "container_gold");
        /*this.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        ScreenManager.getInstance().showScreen(navigateTo, params);
                        return false;
                    }
                }
        );*/
    }
}
