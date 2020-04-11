package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.singletons.Assets;

public class HighScoreButton extends TextButton {
    public HighScoreButton(String text) {
        super(text, Assets.instance.getManager().get(Assets.orangeUiSkin), "default");
    }
}
