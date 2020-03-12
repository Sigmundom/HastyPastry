package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class MenuButton extends TextButton {

    public MenuButton(Assets assets, String text, final ScreenEnum navigateTo) {
        super(text, assets.getManager().get(Assets.uiSkin), "container_gold");
//        this.setTransform(true);
//        this.setScale(0.05f);
        this.addListener(
            new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenManager.getInstance().showScreen(navigateTo);
                    return false;
                }
            }
        );
    }
}
