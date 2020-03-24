package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.mygdx.hastypastry.Assets;

public class UndoButton extends ImageButton {

    public UndoButton() {
        super(Assets.instance.getManager().get(Assets.uiSkin), "play");
    }
}
