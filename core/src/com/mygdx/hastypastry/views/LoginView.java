package com.mygdx.hastypastry.views;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.Config;

public class LoginView extends BaseView {
    private TextField input;
    @Override
    public void buildStage() {
        input = new TextField("", Assets.instance.getManager().get(Assets.uiSkin), "input");
        input.setPosition(Config.UI_WIDTH/2 - 50, Config.UI_HEIGHT/2);
        input.setWidth(200);
        input.setHeight(80);
        input.setMessageText("Write your name here");
        input.setFocusTraversal(true);
        ui.addActor(input);
    }
}
