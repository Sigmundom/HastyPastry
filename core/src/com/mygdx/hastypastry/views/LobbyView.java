package com.mygdx.hastypastry.views;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.MenuButton;

public class LobbyView extends BaseView {
    private MenuButton MenuBtn;

    @Override
    public void buildStage() {
        MenuBtn = new MenuButton("Main Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.HEIGHT - MenuBtn.getHeight() - 10);
        this.ui.addActor(MenuBtn);
    }
}
