package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.listeners.MyButtonListener;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.ChallengeBox;
import com.mygdx.hastypastry.ui.LobbyUser;
import com.mygdx.hastypastry.ui.RecievedChallengeBox;
import com.mygdx.hastypastry.ui.StyledTextButton;
import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private User user;
    private List<User> lobbyList = new ArrayList<>();
    private VerticalGroup lobbyListUI;
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
        DBManager.instance.getDB().joinLobby(user);
    }

    public void initLobbyView(Stage ui, VerticalGroup lobbyListUI) {
        this.ui = ui;
        this.lobbyListUI = lobbyListUI;
        for (User u : lobbyList) {
            if (!u.getFBID().equals(user.getFBID())) {
                addUserUI(u);
            }
        }
    }

    public boolean lobbyListContains(User user) {
        for (User u : lobbyList) {
            if (u.getFBID().equals(user.getFBID())) {
                return true;
            }
        }
        return false;
    }

    public void initCompleteMultiplayerView(Stage ui) {
        this.ui = ui;
    }

    private void addUserUI(final User user) {
        if (lobbyListUI != null) {
            lobbyListUI.addActor(new LobbyUser(this, user));
        }
    }

    public void challengeUser(User opponent) {
        DBManager.instance.getDB().challengePlayer(opponent); //u is opponent
        challengeBox = new ChallengeBox(Lobby.this, opponent);
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
        if (lobbyListUI != null) {
            lobbyListUI.removeActor(leavingUser.getUserUI());
        }
    }

    public List<User> getLobbyList() {
        return lobbyList;
    }

    public void startGame(Match match, boolean playerIsChallenger) {
        Game game = new Game(match, playerIsChallenger, this);
        DBManager.instance.getDB().startGame(game);
        ScreenManager.getInstance().showScreen(
                ScreenEnum.DRAW, game
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
        DBManager.instance.getDB().declineChallenge(match);
    }

    public User getUser() { return user; }

    public void challengeCanceled() {
        if (recievedChallengeBox != null) {
            recievedChallengeBox.hide();
        }
    }

    public void challengeDeclined() {
        challengeBox.hide();
    }

    public void updateUser(User updatedUser) {
        User user = null;
        for (User u : lobbyList) {
            if (u.getFBID().equals(updatedUser.getFBID())) {
                user = u;
                break;
            }
        }

        String oldStatus = user.getStatus();
        String newStatus = updatedUser.getStatus();

        user.setStatus(newStatus);
        user.setChallenge(updatedUser.getChallenge());

        if (user != null) {
            if (oldStatus.equals("inGame") && newStatus.equals("ready")) {
                addUserUI(user);
            } else if (oldStatus.matches("ready|busy") && newStatus.equals("inGame")) {
                removeUserUI(user);
            } else if (oldStatus.equals("ready") && newStatus.equals("busy")) {
                setEnabledUserUI(user, true);
            } else if (oldStatus.equals("busy") && newStatus.equals("ready")) {
                setEnabledUserUI(user, false);
            }
        } else {
            System.out.println("OldUser is null!");
        }
    }

    public void setEnabledUserUI(User user, boolean disabled) {
        if (user.getUserUI() != null) {
            user.getUserUI().getActor().setDisabled(disabled);
            user.getUserUI().getActor().setTouchable(disabled ? Touchable.disabled : Touchable.enabled);
        } else {
            System.out.println("Tried to update an User without an UI!");
        }
    }

}
