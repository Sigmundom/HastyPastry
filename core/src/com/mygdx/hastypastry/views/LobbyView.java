package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.Assets;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.ui.MenuButton;

import java.util.Set;


public class LobbyView extends BaseView {
    private MenuButton MenuBtn;
    private String name;
    private Lobby lobby;
    private BitmapFont font = new BitmapFont();

    public LobbyView(Assets assets, String name) {
        super(assets);
        this.name = name;
        lobby = new Lobby(name, ui);
        font.setColor(Color.BLACK);
        font.getData().setScale(0.05f);
        lobby.acceptChallange("Birte");
    }

    @Override
    public void buildStage() {
        MenuBtn = new MenuButton(assets,"Menu", ScreenEnum.MAIN_MENU);
        MenuBtn.setPosition(10, Config.UI_HEIGHT- MenuBtn.getHeight() - 10);
        this.ui.addActor(MenuBtn);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        Set<String> users = lobby.getLobbyList(name).keySet();
        int y = 12;
        for (CharSequence user : users) {
            font.draw(batch, user, 9, y, 5, 1, false);
            y-= 2;
        }
    }

    @Override
    public void hide() {
        lobby.exitLobby();
    }
}
