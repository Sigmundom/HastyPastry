package com.mygdx.hastypastry.desktop;

import com.mygdx.hastypastry.interfaces.HastyPastryDatabase;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.Match;
import com.mygdx.hastypastry.models.User;

public class DesktopDatabase implements HastyPastryDatabase {

    @Override
    public void subscribeLobbyList(Lobby lobby) {

    }

    @Override
    public void joinLobby(User user) {

    }

    @Override
    public void challengePlayer(User opponent) {

    }

    @Override
    public void declineChallenge(Match match) {

    }

    @Override
    public void acceptChallenge(Match match) {

    }

    @Override
    public void exitLobby() {

    }

    @Override
    public void startGame(Game game) {

    }

    @Override
    public void ready(Game game) {

    }

    @Override
    public void exitMatch() {

    }

    @Override
    public void checkVersion() {

    }

    @Override
    public void withdrawChallenge(User opponent) {

    }
}
