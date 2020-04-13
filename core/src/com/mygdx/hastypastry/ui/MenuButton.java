package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.models.MusicAndSound;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class MenuButton extends TextButton {
    private MusicAndSound musicAndSound = new MusicAndSound();

    public MenuButton(String text, final ScreenEnum navigateTo, final Object... params) {
        super(text, Assets.instance.getManager().get(Assets.orangeUiSkin), "default");
        this.addListener(
            new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    musicAndSound.getButtonSound().play();
                    ScreenManager.getInstance().showScreen(navigateTo, params);
                    return false;
                }
            }
        );
    }
}
