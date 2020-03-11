package com.mygdx.hastypastry.views;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.MenuButton;

public class MainMenuView extends BaseView {

    private MenuButton SinglePlayerBtn;
    private MenuButton MultiPlayerBtn;

    public MainMenuView() {
        super();
    }

    @Override
    public void buildStage() {

        SinglePlayerBtn = new MenuButton("Single Player", ScreenEnum.PLAY);
        SinglePlayerBtn.setPosition(Config.WIDTH/2 - SinglePlayerBtn.getWidth() - 10,Config.HEIGHT/2);
        MultiPlayerBtn = new MenuButton("Multiplayer", ScreenEnum.LOBBY);
        MultiPlayerBtn.setPosition(Config.WIDTH/2 + 10,Config.HEIGHT/2);
        this.ui.addActor(SinglePlayerBtn);
        this.ui.addActor(MultiPlayerBtn);
    }

}
