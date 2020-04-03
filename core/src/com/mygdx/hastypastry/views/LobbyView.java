package com.mygdx.hastypastry.views;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.ui.MenuButton;

public class LobbyView extends BaseView {
    private Lobby lobby;
    private Table lobbyTable;

    public LobbyView(Lobby lobby) {
        this.lobby = lobby;

    }

    @Override
    public void buildStage() {
        // Creates and adds menuButton to ui.
        MenuButton menuBtn = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuBtn.setPosition(10, Config.UI_HEIGHT- menuBtn.getHeight() - 10);
        this.ui.addActor(menuBtn);

        // Creates lobbyTable
        lobbyTable = new Table();

        //Set table to fill stage
        lobbyTable.setFillParent(true);

        //Set padding
        lobbyTable.padTop(100);
        lobbyTable.padLeft(100);
        lobbyTable.padRight(100);

        //Set alignment of contents in the table.
        lobbyTable.top();

        // Sync lobbyTable with lobbyList
        lobby.initLobbyView(ui, lobbyTable);

        // Add lobbyTable to ui
        ui.addActor(lobbyTable);
    }

    @Override
    public void hide() {
        lobby.exitLobby();
    }
}
