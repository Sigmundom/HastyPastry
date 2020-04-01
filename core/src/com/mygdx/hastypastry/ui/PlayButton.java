package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.mygdx.hastypastry.singletons.Assets;

public class PlayButton extends ImageButton {

    public PlayButton() {
        super(Assets.instance.getManager().get(Assets.uiSkin), "play");
    }
}
