package com.mygdx.hastypastry.views;

import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.ui.MenuButton;


public class LobbyView extends BaseView {
    private MenuButton MenuBtn;
    private String name;
    private Lobby lobby;

    public LobbyView(Assets assets, String name) {
        super(assets);
        this.name = name;
        lobby = new Lobby(name);
    }

    @Override
    public void buildStage() {
        MenuBtn = new MenuButton(assets,"Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.UI_HEIGHT- MenuBtn.getHeight() - 10);
        this.ui.addActor(MenuBtn);
    }

    @Override
    public void hide() {
        lobby.exitLobby(name);
    }
}
