package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.MusicAndSound;

public class StyledTextButton extends TextButton {
    public StyledTextButton(String text) {
        super(text, Assets.instance.getManager().get(Assets.orangeUiSkin), "default");
    }
}
