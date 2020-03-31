package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.singletons.ScreenManager;

import java.util.HashMap;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class Lobby {
    private final String DB_PATH = "lobby";
    private String playerName;
    private Stage ui;
    private Map<String, Object> lobbyList = new HashMap<>();

    public Lobby(String playerName, Stage ui) {
        this.playerName = playerName;
        joinLobby();
        this.ui = ui;
    }

    private void joinLobby() {
        // Listen for changes in lobby list
        GdxFIRDatabase.inst()
                .inReference(DB_PATH)
                .onDataChange(Map.class)
                .then(new Consumer<HashMap<String, Object>>() {
                    @Override
                    public void accept(HashMap<String, Object> list) {
                        System.out.println("change");
                        System.out.println(list);
                        updateLobby(list);
                        lobbyList = list;  // Kan fjernes om vi bruka stage
                    }
                })
                .then(  // Add player to lobby
                        GdxFIRDatabase.inst()
                                .inReference(DB_PATH + "/" + playerName)
                                .setValue("ready")
                );

        // Listen for challanges
        GdxFIRDatabase.inst()
                .inReference(String.format("%s/%s", DB_PATH, playerName))
                .onDataChange(String.class)
                .then(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        if (s != "ready") {
                            System.out.println(s + " has challenged you!");
                        }
                    }
                });
    }

    public void challangePlayer(final String opponentName) {
        GdxFIRDatabase.inst()
                .inReference(String.format("%s/%s", DB_PATH, opponentName))
                .setValue(playerName);
    }

    public void acceptChallange(final String opponentName) {
        final String gameID = playerName+opponentName;
        String gamePath = "games/"+gameID+"/";
        GdxFIRDatabase.inst()
                .inReference(gamePath+playerName)
                .setValue(playerName)
                .then(
                        GdxFIRDatabase.inst()
                        .inReference(gamePath+opponentName)
                        .setValue(opponentName)
                ).then(new Consumer<Void>() {
            @Override
            public void accept(Void aVoid) {
                Game game = new Game(gameID, playerName, opponentName, new Level("Level 1"));
                ScreenManager.getInstance().showScreen(ScreenEnum.DRAW, game);
            }
        });
    }

    // Kan fjernes om vi bruka stage
    public void exitLobby() {
        GdxFIRDatabase.inst()
                .inReference(DB_PATH + "/" + playerName)
                .removeValue();
    }

    public Map<String, Object> getLobbyList(String name) {
        //Removes current user from the local lobby list before returning it
        Map<String, Object> filteredList = new HashMap<>(lobbyList);
        filteredList.remove(playerName);
        return filteredList;
    }

    private void updateLobby(HashMap<String, Object> newList) {
        if (lobbyList.size() > newList.size()) {
            Map<String, Object> diff = new HashMap<>(lobbyList);
            diff.entrySet().removeAll(newList.entrySet());
            System.out.println("Update");
            System.out.println(diff);

            // Fjern actor her

        } else if (newList.size() > lobbyList.size()) {
            Map<String, Object> diff = new HashMap<>(newList);
            diff.entrySet().removeAll(lobbyList.entrySet());
            System.out.println("Update");
            System.out.println(diff);

            // Legg til actor her

        }
    }

}
