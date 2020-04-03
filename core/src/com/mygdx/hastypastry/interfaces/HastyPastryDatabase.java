package com.mygdx.hastypastry.interfaces;

import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.Match;
import com.mygdx.hastypastry.models.User;

public interface HastyPastryDatabase {
    void subscribeLobbyList(Lobby lobby);
    void joinLobby(Lobby lobby, User user);
    void exitLobby(String FBID);
    void challengePlayer(Lobby lobby, User opponent, User player);
    void declineChallenge(Match match, User player);
    void acceptChallenge(Match match);
}
