package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.singletons.Assets;

public class LabelButton extends TextButton {
    public LabelButton(String text) {
        super(text, Assets.instance.getManager().get(Assets.uiSkin), "container_blue");
    }
}
