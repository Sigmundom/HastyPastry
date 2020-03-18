package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class Lobby {
    private final String DB_PATH = "lobby";
    private Stage ui;
    private Map<String, Object> lobbyList = new HashMap<>();

    public Lobby(String name, Stage ui) {
        joinLobby(name);
        this.ui = ui;
    }

    private void joinLobby(final String name) {
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
                .then(
                        GdxFIRDatabase.inst()
                                .inReference(DB_PATH + "/" + name)
                                .setValue("ready")
                );
    }

    // Kan fjernes om vi bruka stage
    public void exitLobby(String name) {
        GdxFIRDatabase.inst()
                .inReference(DB_PATH + "/" + name)
                .removeValue();
    }

    public Map<String, Object> getLobbyList(String name) {
        //Removes current user from the local lobby list before returning it
        Map<String, Object> filteredList = new HashMap<>(lobbyList);
        filteredList.remove(name);
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
