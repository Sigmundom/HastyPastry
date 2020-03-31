package com.mygdx.hastypastry.models;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.annotations.MapConversion;
import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.database.FirebaseMapConverter;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class Lobby {
    private final String DB_PATH = "lobby";
    private User user;
    private Table lobbyTable;
    private List<User> lobbyList = new ArrayList<>();

    public Lobby(String playerName, Table lobbyTable) {
        user = new User(playerName);
        this.lobbyTable = lobbyTable;
        GdxFIRDatabase.inst().setMapConverter(new FirebaseMapConverter() {
            @Override
            public <T> T convert(Map<String, Object> map, Class<T> aClass) {
                return (T) new User((String)map.get("name"), (boolean)map.get("ready"), (String)map.get("challenger"));
            }

            @Override
            public Map<String, Object> unConvert(Object o) {
                return null;
            }
        });
    }

    public void joinLobby() {
        GdxFIRDatabase.inst().inReference(DB_PATH).readValue(List.class)
                .then(new Consumer<List<User>>() {
                    @Override
                    @MapConversion(User.class)
                    public void accept(List<User> list) {
                        System.out.println(list);
                        boolean nameTaken = false;
                        for (User u : list) {
                            if (u.getName().equals(user.getName())) {
                                nameTaken = true;
                            }
                        }
                        if (nameTaken) {
                            System.out.println("Name is already taken!");
                        } else {
                            updateLobbyList(list);
                            GdxFIRDatabase.promise()
                                    .then(GdxFIRDatabase.inst().inReference(DB_PATH).push().setValue(user))
    //                                .then(GdxFIRDatabase.inst().inReference(DB_PATH).onDataChange(List.class))
                                    .then(GdxFIRDatabase.inst().inReference(DB_PATH).onChildChange(List.class, ChildEventType.ADDED))
                                    .then(new Consumer<List<User>>() {
                                        @Override
                                        @MapConversion(User.class)
                                        public void accept(List<User> list) {
                                            updateLobbyList(list);
                                        }
                                    });
                        }

                    }
                });
    }

    private void updateLobbyList(List<User> list) {
        for (User u : list) {
            MenuButton userBtn = new MenuButton(u.getName(), ScreenEnum.DRAW, new Game("abc", u.getName(), user.getName(), new Level1()));
            lobbyTable.add(userBtn);
            lobbyTable.row();
        }
    }

//    private void joinLobby() {
//        // Fetch list and update if name isn't already taken
//        GdxFIRDatabase.inst()
//                .inReference(DB_PATH)
//                .readValue(Map.class)
//                .then(new Consumer<HashMap<String, Object>>() {
//                    @Override
//                    public void accept(HashMap<String, Object> list) {
//                        if (list.containsKey(playerName)) {
//                            System.out.println("Name already taken!");
//                        } else {
//                            updateLobby(list);
//                            lobbyList = list;  // Kan fjernes om vi bruka stage
//                        }
//                    }
//                })
//                .then(  // Add player to lobby
//                        GdxFIRDatabase.inst()
//                                .inReference(DB_PATH + "/" + playerName)
//                                .setValue("ready")
//                )
//                .then(new Consumer<Void>() {
//                    @Override
//                    public void accept(Void aVoid) {
//                        // Listen for changes in lobby list
//                        GdxFIRDatabase.inst()
//                                .inReference(DB_PATH)
//                                .onDataChange(Map.class)
//                                .then(new Consumer<HashMap<String, Object>>() {
//                                    @Override
//                                    public void accept(HashMap<String, Object> list) {
//                                        System.out.println("change");
//                                        System.out.println(list);
//                                        updateLobby(list);
//                                        lobbyList = list;  // Kan fjernes om vi bruka stage
//                                    }
//                                });
//                    }
//                });
//
//        // Listen for challanges
//        GdxFIRDatabase.inst()
//                .inReference(String.format("%s/%s", DB_PATH, playerName))
//                .onDataChange(String.class)
//                .then(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        if (s != "ready") {
//                            System.out.println(s + " has challenged you!");
//                        }
//                    }
//                });
//    }

//    public void challangePlayer(final String opponentName) {
//        GdxFIRDatabase.inst()
//                .inReference(String.format("%s/%s", DB_PATH, opponentName))
//                .setValue(playerName);
//    }
//
//    public void acceptChallange(final String opponentName) {
//        final String gameID = playerName+opponentName;
//        String gamePath = "games/"+gameID+"/";
//        GdxFIRDatabase.inst()
//                .inReference(gamePath+playerName)
//                .setValue(playerName)
//                .then(
//                        GdxFIRDatabase.inst()
//                        .inReference(gamePath+opponentName)
//                        .setValue(opponentName)
//                ).then(new Consumer<Void>() {
//            @Override
//            public void accept(Void aVoid) {
//                Game game = new Game(gameID, playerName, opponentName, new Level1());
//                ScreenManager.getInstance().showScreen(ScreenEnum.DRAW, game);
//            }
//        });
//    }
//
//    // Kan fjernes om vi bruka stage
    public void exitLobby() {
//        GdxFIRDatabase.inst()
//                .inReference(DB_PATH + "/" + playerName)
//                .removeValue();
    }
//
    public List<User> getLobbyList(String name) {
        //Removes current user from the local lobby list before returning it
//        Map<String, Object> filteredList = new HashMap<>(lobbyList);
//        filteredList.remove(playerName);
        return lobbyList;
    }
//
//    private void updateLobby(HashMap<String, Object> newList) {
//        if (lobbyList.size() > newList.size()) {
//            Map<String, Object> diff = new HashMap<>(lobbyList);
//            diff.entrySet().removeAll(newList.entrySet());
//            System.out.println("Update");
//            System.out.println(diff);
//
//            // Fjern actor her
//
//        } else if (newList.size() > lobbyList.size()) {
//            Map<String, Object> diff = new HashMap<>(newList);
//            diff.entrySet().removeAll(lobbyList.entrySet());
//            System.out.println("Update");
//            System.out.println(diff);
//
//            // Legg til actor her
//
//        }
//    }

}
