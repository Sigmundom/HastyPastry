package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.LabelButton;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private User user;
    private List<User> lobbyList = new ArrayList<>();
    private Table lobbyTable;

    public Lobby() {
        // Get list of users in lobby and listens to changes
        DBManager.instance.getDB().subscribeLobbyList(this);
    }

    public boolean isNameTaken(String name) {
        for (User u : lobbyList) {
            if (u.getName() == name) {
                return true;
            }
        }
        return false;
    }

    public void joinLobby(String name) {
        user = new User(name);
        DBManager.instance.getDB().joinLobby(this, user);
    }

    public void exitLobby() {
        DBManager.instance.getDB().exitLobby(user.getFBID());
    }

    public void initLobbyTable(Table lobbyTable) {
        this.lobbyTable = lobbyTable;
        for (User u : lobbyList) {
            addUserUI(lobbyTable, u);
        }
    }

    private void addUserUI(Table lobbyTable, final User u) {
        LabelButton lobbyUser = new LabelButton(u.getName());
        lobbyUser.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                DBManager.instance.getDB().challangePlayer(Lobby.this, u.getFBID(), user.getName());
                return false;
            }
        });
        lobbyTable.add(lobbyUser).growX();
        lobbyTable.row();
    }

    public void addUser(User newUser) {
        lobbyList.add(newUser);
        if (lobbyTable != null) {
            addUserUI(lobbyTable, newUser);
        }
    }

    public void removeUser(User leavingUser) {
        lobbyList.remove(leavingUser);
        if (lobbyTable != null) {
            for (Actor e : lobbyTable.getChildren()) {
                if (((LabelButton)e).getText().toString().equals(leavingUser.getName())) {
                    lobbyTable.removeActor(e);
                    break;
                }
            }
        }
    }

    public List<User> getLobbyList() {
        return lobbyList;
    }

    public void startGame(String opponentName) {
        ScreenManager.getInstance().showScreen(
                ScreenEnum.DRAW, new Game("GameID", opponentName, user.getName(), new Level("Level 2"))
        );
    }
}
