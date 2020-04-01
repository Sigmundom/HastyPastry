package com.mygdx.hastypastry.interfaces;

import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.User;

public interface HastyPastryDatabase {
    void subscribeLobbyList(Lobby lobby);
    void joinLobby(Lobby lobby, User user);
    void exitLobby(String FBID);
    void challangePlayer(Lobby lobby, String opponentFBID, String playerName);

}
