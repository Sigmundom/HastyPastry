package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.ChallengeBox;
import com.mygdx.hastypastry.ui.RecievedChallengeBox;
import com.mygdx.hastypastry.ui.StyledTextButton;
import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private User user;
    private List<User> lobbyList = new ArrayList<>();
    private Table lobbyTable;
    private Stage ui;
    private RecievedChallengeBox recievedChallengeBox; // Accept or decline a challenge.
    private ChallengeBox challengeBox; // Waiting for response / Cancel challenge.

    public Lobby() {
        // Get list of users in lobby and listens to changes
        DBManager.instance.getDB().subscribeLobbyList(this);
    }

    public boolean isNameTaken(String name) {
        for (User u : lobbyList) {
            if (u.getName().equals(name)) {
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

    public void initLobbyView(Stage ui, Table lobbyTable) {
        this.ui = ui;
        this.lobbyTable = lobbyTable;
        for (User u : lobbyList) {
            if (!u.getFBID().equals(user.getFBID())) {
                addUserUI(u);
            }
        }
    }

    public void initCompleteMultiplayerView(Stage ui) {
        this.ui = ui;
    }

    private void addUserUI(final User u) {
        if (lobbyTable != null) {
            StyledTextButton userButton = new StyledTextButton(u.getName());
            userButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    challengeUser(u);
                    return false;
                }
            });
            userButton.setDisabled(u.getStatus().equals("busy"));
            u.setUserButton(userButton);
            lobbyTable.add(userButton).growX().padBottom(10);
            lobbyTable.row();
        }
    }

    public void challengeUser(User u) {
        final String matchID = user.getFBID(); // We use challengers ID as matchID.
        Match match = new Match(matchID, user, u);
        DBManager.instance.getDB().challengePlayer(Lobby.this, u, user, match); //u is opponent
        challengeBox = new ChallengeBox(Lobby.this, match, u);
        challengeBox.show(ui);
    }

    public void addUser(User newUser) {
        lobbyList.add(newUser);
        addUserUI(newUser);
    }

    public void removeUser(User leavingUser) {
        lobbyList.remove(leavingUser);
        removeUserUI(leavingUser);
    }

    private void removeUserUI(User leavingUser) {
        if (lobbyTable != null) {
            lobbyTable.removeActor(leavingUser.getUserButton());
        }
    }

    public List<User> getLobbyList() {
        return lobbyList;
    }

    public void startGame(Match match, boolean challenger) {
        DBManager.instance.getDB().startGame(user);
        ScreenManager.getInstance().showScreen(
                ScreenEnum.DRAW, new Game(match, challenger, this)
        );
    }
    private String randLevel(){
        int numberOfLevels = Level.getNumberOfLevels();
        int rand = (int)(Math.random()*numberOfLevels)+1;
        return "Level "+ rand;
    }

    public void receivedChallenge(Match match) {
        recievedChallengeBox = new RecievedChallengeBox(this, match);
        recievedChallengeBox.show(ui);
    }

    public void acceptChallenge(Match match) {
        String level = randLevel();
        match.setLevel(level);
        DBManager.instance.getDB().acceptChallenge(match);
        startGame(match, false);
    }

    public void declineChallenge(Match match) {
        DBManager.instance.getDB().declineChallenge(match, user);
    }

    public User getUser() { return user; }

    public void withdrawChallenge(Match match, User opponent) {
        DBManager.instance.getDB().declineChallenge(match, opponent);
    }

    public void challengeCanceled() {
        if (recievedChallengeBox != null) {
            recievedChallengeBox.hide();
        }
    }

    public void challengeDeclined() {
        challengeBox.hide();
    }

    public void updateUser(User updatedUser) {
        User oldUser = null;
        for (User u : lobbyList) {
            if (u.getFBID().equals(updatedUser.getFBID())) {
                oldUser = u;
                break;
            }
        }

        String oldStatus = oldUser.getStatus();
        String newStatus = updatedUser.getStatus();

        if (oldUser != null) {
            if (oldStatus.equals("inGame") && newStatus.equals("ready")) {
                addUserUI(updatedUser);
            } else if (oldStatus.matches("ready|busy") && newStatus.equals("inGame")) {
                removeUserUI(oldUser);
            } else if (oldStatus.equals("ready") && newStatus.equals("busy")) {
                setEnabledUserUI(oldUser, true);
            } else if (oldStatus.equals("busy") && newStatus.equals("ready")) {
                setEnabledUserUI(oldUser, false);
            }
        } else {
        }
    }

    private void setEnabledUserUI(User user, boolean disabled) {
        if (user.getUserButton() != null) {
            user.getUserButton().setDisabled(disabled);
        }
    }

}
