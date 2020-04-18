package com.mygdx.hastypastry.views;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.ui.MenuButton;

public class LobbyView extends BaseView {
    private Lobby lobby;

    public LobbyView(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public void buildStage() {
        // Creates lobbyTable
        VerticalGroup lobbyListUI = new VerticalGroup();
        lobbyListUI.pad(100,0,100,0);
        lobbyListUI.space(10); // Space between items
        lobbyListUI.top(); //Set alignment of contents in the group.

        // Connect the lobbyView to the lobby model
        lobby.initLobbyView(ui, lobbyListUI);

        // Puts the lobbyListUI in a Scroll Pane
        ScrollPane scrollPane = new ScrollPane(lobbyListUI, Assets.instance.getManager().get(Assets.orangeUiSkin), "no-bg");
        scrollPane.setFillParent(true);

        // Add lobbyList to ui
        ui.addActor(scrollPane);

        // Creates and adds menuButton to ui.
        MenuButton menuBtn = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuBtn.setPosition(10, Config.UI_HEIGHT- menuBtn.getHeight() - 10);
        this.ui.addActor(menuBtn);
    }
}
