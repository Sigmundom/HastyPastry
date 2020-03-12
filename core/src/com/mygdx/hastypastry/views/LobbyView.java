package com.mygdx.hastypastry.views;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.ui.MenuButton;

public class LobbyView extends BaseView {
    private MenuButton MenuBtn;

    public LobbyView(Assets assets) {
        super(assets);
    }

    @Override
    public void buildStage() {
        MenuBtn = new MenuButton(assets,"Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.UI_HEIGHT- MenuBtn.getHeight() - 10);
        this.ui.addActor(MenuBtn);
    }
}
