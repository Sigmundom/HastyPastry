package com.mygdx.hastypastry.interfaces;

import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.dbmodels.Match;
import com.mygdx.hastypastry.models.dbmodels.User;

public interface HastyPastryDatabase {
    // Lobby methods
    void subscribeLobbyList(Lobby lobby);
    void joinLobby(User user);
    void challengePlayer(User opponent);
    void declineChallenge(Match match);
    void acceptChallenge(Match match);
    void exitLobby();

    // In-game methods
    void startGame(Game game);
    void ready(Game game);
    void exitMatch();

    void checkVersion();

    void withdrawChallenge(User opponent);
}
