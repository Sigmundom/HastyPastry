package com.mygdx.hastypastry.interfaces;

import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.User;

public interface HastyPastryDatabase {
    void subscribeLobbyList(Lobby lobby);
    void joinLobby(User user);
    void exitLobby(String FBID);
}
