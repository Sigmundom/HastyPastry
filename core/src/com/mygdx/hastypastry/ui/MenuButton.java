package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class MenuButton extends TextButton {

    public MenuButton(String text, final ScreenEnum navigateTo) {
        super(text, Assets.manager.get(Assets.uiSkin), "container_gold");
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
