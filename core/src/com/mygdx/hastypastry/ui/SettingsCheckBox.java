package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.mygdx.hastypastry.singletons.Assets;

public class SettingsCheckBox extends CheckBox {

    public SettingsCheckBox() {
        super(null, Assets.instance.getManager().get(Assets.uiCheckboxSkin));
    }
}
