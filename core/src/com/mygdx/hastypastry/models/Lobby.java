package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.controllers.FileReader;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.ChallengeBox;
import com.mygdx.hastypastry.ui.RecievedChallengeBox;
import com.mygdx.hastypastry.ui.LabelButton;
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
            if (u.getFBID() != user.getFBID()){
                addUserUI(lobbyTable, u);
            }
        }
    }

    private void addUserUI(Table lobbyTable, final User u) {
        LabelButton userButton = new LabelButton(u.getName());
        userButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                challengeUser(u);
                return false;
            }
        });
        lobbyTable.add(userButton).growX().padBottom(10);
        lobbyTable.row();
    }

    private void challengeUser(User u) {
        final String matchID = user.getFBID(); // We use challengers ID as matchID.
        Match match = new Match(matchID, user.getName(), u.getName());
        DBManager.instance.getDB().challengePlayer(Lobby.this, u, user, match); //u is opponent
        challengeBox = new ChallengeBox(Lobby.this, match, u);
        challengeBox.show(ui);
    }

    public void addUser(User newUser) {
        lobbyList.add(newUser);
        if (lobbyTable != null) {
            addUserUI(lobbyTable, newUser);
        }
    }

    public void removeUser(User leavingUser) {
        lobbyList.remove(leavingUser);
        if (lobbyTable != null) {
            for (Actor e : lobbyTable.getChildren()) {
                if (((LabelButton)e).getText().toString().equals(leavingUser.getName())) {
                    lobbyTable.removeActor(e);
                    break;
                }
            }
        }
    }

    public List<User> getLobbyList() {
        return lobbyList;
    }

    public void startGame(Match match, boolean challenger) {
        ScreenManager.getInstance().showScreen(
                ScreenEnum.DRAW, new Game(match, challenger)
        );
    }
    private String randLevel(){
        FileReader reader = new FileReader();
        ArrayList<String> fileData = new ArrayList<>();
        fileData = reader.getInternalFileData("levels.txt");
        int i=0;
        for(String line : fileData){
            if(line.contains("Level")){
                i++;
            }
        }
        int rand = (int)(Math.random()*i)+1;
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
        ScreenManager.getInstance().showScreen(ScreenEnum.DRAW, new Game(match, false));
    }

    public void declineChallenge(Match match) {
        DBManager.instance.getDB().declineChallenge(match, user);
    }

    public void withdrawChallenge(Match match, User opponent) {
        DBManager.instance.getDB().declineChallenge(match, opponent);
    }

    public void challengeCanceled() {
        recievedChallengeBox.hide();
    }

    public void challengeDeclined() {
        challengeBox.hide();
    }
}
