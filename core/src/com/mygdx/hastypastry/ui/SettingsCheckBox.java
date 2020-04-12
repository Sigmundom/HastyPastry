package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Scaling;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.singletons.Assets;

public class SettingsCheckBox extends CheckBox {

    public SettingsCheckBox() {
        super(null, Assets.instance.getManager().get(Assets.orangeUiSkin), "default");
        this.getImage().setScaling(Scaling.fill);
        this.getImageCell().size(Config.UI_WIDTH/10);
    }
}
