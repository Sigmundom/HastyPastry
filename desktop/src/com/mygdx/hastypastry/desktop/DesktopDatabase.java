package com.mygdx.hastypastry.desktop;

import com.mygdx.hastypastry.interfaces.HastyPastryDatabase;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.User;

public class DesktopDatabase implements HastyPastryDatabase {

    @Override
    public void subscribeLobbyList(Lobby lobby) {

    }

    @Override
    public void joinLobby(Lobby lobby, User user) {

    }

    @Override
    public void exitLobby(String FBID) {

    }

    @Override
    public void challangePlayer(Lobby lobby, String opponentFBID, String playerName) {

    }
}
