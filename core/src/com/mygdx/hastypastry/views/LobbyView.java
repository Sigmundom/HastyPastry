package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.User;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.PlayButton;

import java.util.Set;


public class LobbyView extends BaseView {
    private MenuButton MenuBtn;
    private String name;
    private Lobby lobby;
    private BitmapFont font = new BitmapFont();
    private Table lobbyTable;

    public LobbyView(String name) {
        this.name = name;
        lobbyTable = new Table();

        //Set table to fill stage
        lobbyTable.setFillParent(true);

        //Set padding
        lobbyTable.padTop(100);
        lobbyTable.padLeft(50);
        lobbyTable.padRight(50);

        //Set alignment of contents in the table.
        lobbyTable.top();
        lobby = new Lobby(name, lobbyTable);
        font.setColor(Color.BLACK);
        font.getData().setScale(0.05f);
    }

    @Override
    public void buildStage() {
        MenuBtn = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.UI_HEIGHT- MenuBtn.getHeight() - 10);
        PlayButton joinBtn = new PlayButton();
        joinBtn.setPosition(Config.UI_WIDTH/2, Config.UI_HEIGHT - 50);
        joinBtn.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        lobby.joinLobby();
                        return false;
                    }
                });
        this.ui.addActor(joinBtn);
        this.ui.addActor(MenuBtn);
        this.ui.addActor(lobbyTable);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
//        Set<String> users = lobby.getLobbyList(name).keySet();
        int y = 12;
        for (User u : lobby.getLobbyList(name)) {
            font.draw(batch, u.getName(), 9, y, 5, 1, false);
            y-= 2;
        }
    }

    @Override
    public void hide() {
        lobby.exitLobby();
    }
}
