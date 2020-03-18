package com.mygdx.hastypastry.models;

import java.util.HashMap;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class Lobby {
    private final String DB_PATH = "lobby";
    private Map<String, Object> lobbyList;

    public Lobby(String name) {
        joinLobby(name);
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
                        lobbyList = list;
                    }
                })
                .then(
                        GdxFIRDatabase.inst()
                                .inReference(DB_PATH + "/" + name)
                                .setValue("ready")
                );
    }

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

}
