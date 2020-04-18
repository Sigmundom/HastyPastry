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
//        Table container = new Table();
//        container.setFillParent(true);
        // Creates lobbyTable
        VerticalGroup lobbyListUI = new VerticalGroup();


        //Set padding
        lobbyListUI.pad(100,0,100,0);

        lobbyListUI.space(10);

        //Set alignment of contents in the table.
        lobbyListUI.top();

        // Sync lobbyTable with lobbyList
        lobby.initLobbyView(ui, lobbyListUI);

        //
        ScrollPane scrollPane = new ScrollPane(lobbyListUI, Assets.instance.getManager().get(Assets.orangeUiSkin), "no-bg");
        scrollPane.setFillParent(true);

        //Set table to fill scrollPane
//        lobbyTable.setFillParent(true);

        // Add lobbyTable to ui
//        container.add(scrollPane).grow();
        ui.addActor(scrollPane);

        // Creates and adds menuButton to ui.
        MenuButton menuBtn = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuBtn.setPosition(10, Config.UI_HEIGHT- menuBtn.getHeight() - 10);

        this.ui.addActor(menuBtn);
    }

    @Override
    public void hide() {}
}
