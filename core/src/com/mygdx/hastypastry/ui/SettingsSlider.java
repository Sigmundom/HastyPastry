package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.mygdx.hastypastry.singletons.Assets;

public class SettingsSlider extends Slider {

    public SettingsSlider() {
        super(0f, 0.7f, 0.05f, false, Assets.instance.getManager().get(Assets.orangeUiSkin), "default-horizontal");
    }
}
