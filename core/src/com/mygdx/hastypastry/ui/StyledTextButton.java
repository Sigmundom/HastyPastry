package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.singletons.Assets;

public class StyledTextButton extends TextButton {
    public StyledTextButton(String text) {
        super(text, Assets.instance.getManager().get(Assets.orangeUiSkin), "default");
    }
}
