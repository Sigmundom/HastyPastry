package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.hastypastry.Assets;

public class PlayButton extends ImageButton {

    public PlayButton(Assets assets) {
        super(assets.getManager().get(Assets.uiSkin), "play");
    }
}
