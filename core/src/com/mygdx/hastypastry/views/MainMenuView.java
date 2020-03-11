package com.mygdx.hastypastry.views;

import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.MenuButton;

public class MainMenuView extends BaseView {

    public MainMenuView() {
        super();
    }

    @Override
    public void buildStage() {
        this.ui.addActor(new MenuButton("Play", ScreenEnum.PLAY));
    }

}
