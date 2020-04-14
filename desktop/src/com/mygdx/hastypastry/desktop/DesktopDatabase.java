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
    public void joinLobby(Lobby lobby, User user) {

    }

    @Override
    public void exitLobby(String FBID) {

    }

    @Override
    public void challengePlayer(Lobby lobby, User opponent, User player, Match match) {

    }

    @Override
    public void declineChallenge(Match match, User player) {

    }

    @Override
    public void acceptChallenge(Match match) {

    }

    @Override
    public void ready(Game game) {

    }

    @Override
    public void exitMatch(Game game) {

    }

    @Override
    public void checkVersion() {

    }

    @Override
    public void startGame(User user) {

    }
}
