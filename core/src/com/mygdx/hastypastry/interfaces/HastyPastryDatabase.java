package com.mygdx.hastypastry.interfaces;

import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.Match;
import com.mygdx.hastypastry.models.Player;
import com.mygdx.hastypastry.models.User;

public interface HastyPastryDatabase {
    // Lobby methods
    void subscribeLobbyList(Lobby lobby);
    void joinLobby(Lobby lobby, User user);
    void exitLobby(String FBID);
    void challengePlayer(Lobby lobby, User opponent, User player);
    void declineChallenge(Match match, User player);
    void acceptChallenge(Match match);

    // In-game methods
    void ready(Game game);
    void exitMatch(Game game);

    void checkVersion();
}
