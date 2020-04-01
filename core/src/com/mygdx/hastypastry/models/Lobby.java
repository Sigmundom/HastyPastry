package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.singletons.DBManager;
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
        DBManager.instance.getDB().joinLobby(user);
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

    private void addUserUI(Table lobbyTable, User u) {
        LabelButton lobbyUser = new LabelButton(u.getName());
        lobbyTable.add(lobbyUser);
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
}
