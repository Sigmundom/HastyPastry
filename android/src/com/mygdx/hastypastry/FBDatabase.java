package com.mygdx.hastypastry;

import com.badlogic.gdx.Gdx;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.HastyPastryDatabase;
import com.mygdx.hastypastry.levels.LevelData;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.Match;
import com.mygdx.hastypastry.models.User;
import com.mygdx.hastypastry.singletons.ScreenManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Communication with Firebase
 * Must be located in /android folder
 */
public class FBDatabase implements HastyPastryDatabase {
    private final DatabaseReference lobbyRef = FirebaseDatabase.getInstance().getReference("lobby");
    private final DatabaseReference matchesRef = FirebaseDatabase.getInstance().getReference("match");
    private ChildEventListener lobbyListener;
    private ValueEventListener challengeListener;
    private ValueEventListener responseListener;
    private ValueEventListener drawingListener;

    /**
     * Called from lobby-class during initialisation.
     * Makes sure that Lobby-Class is updated on changes of Lobby participants, challenges, and matches.
     * @param lobby
     */
    public void subscribeLobbyList(final Lobby lobby) {
        lobbyListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User newUser = dataSnapshot.getValue(User.class);
                // FBID is used to remove the user from FB when exiting the lobby.
                newUser.setFBID(dataSnapshot.getKey());
                lobby.addUser(newUser);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User updatedUser = dataSnapshot.getValue(User.class);

                lobby.updateUser(updatedUser);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String leavingUserFBID = dataSnapshot.getKey();
                for (User u : lobby.getLobbyList()) {
                    if (u.getFBID().equals(leavingUserFBID)) {
                        lobby.removeUser(u);
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        lobbyRef.addChildEventListener(lobbyListener);
    }

    /**
     * Called from Lobby when a user presses the join lobby button in the login view.
     * Listens for challenges to the user, by listening to changes to the value.
     * The challenge will be a field that has a value when a challenge is posed to the player.
     *
     * @param lobby
     * @param user
     */
    @Override
    public void joinLobby(final Lobby lobby, User user) {
        DatabaseReference userRef = lobbyRef.push();
        user.setFBID(userRef.getKey());
        userRef.setValue(user);
        challengeListener = new ValueEventListener() {
            // Listen for challenges
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Match challenge = dataSnapshot.getValue(Match.class);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        if (challenge == null) {
                            lobby.challengeCanceled();
                        } else {
                            lobby.receivedChallenge(challenge);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userRef.child("challenge").addValueEventListener(challengeListener);
    }


    /**
     * Important to remove the player and it's listeners when the player exits the lobby.
     * @param FBID
     */
    public void exitLobby(String FBID) {
        lobbyRef.removeEventListener(lobbyListener);
        lobbyRef.child(FBID).child("challenge").removeEventListener(challengeListener);
        lobbyRef.child(FBID).removeValue();
    }


    /**
     * Used by Lobby class to pose a challenge to another player
     * Will thereafter wait for a response, by listening for changes to the value.
     * Depending of the actions of the opponent, the challenged can be denied or accepted.
     * In our case, if the level is set by the opponent, the challenge is accepted
     * If the opponent deletes the match, it is denied.
     * @param lobby
     * @param opponent
     * @param player
     * @param match
     */
    @Override
    public void challengePlayer(final Lobby lobby, User opponent, User player, Match match) {
        final String matchID = match.getMatchID();

        // Creates a new match and adds it to firebase.
        matchesRef.child(matchID).setValue(match);

        // Setting player's ready field to false to prevent new challenges.
        lobbyRef.child(player.getFBID()).child("status").setValue("busy");

        // Updating the challenged player's ready and challenger fields.
        lobbyRef.child(opponent.getFBID()).child("status").setValue("busy");
        lobbyRef.child(opponent.getFBID()).child("challenge").setValue(match);


        responseListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Opponent accepts challenge, by selecting a Level or declines by removing the match.
                final Match match = dataSnapshot.getValue(Match.class);
                if (match != null) {
                    if (match.getLevel() != null) {
                        if (match.getLevel().matches("Level \\d+")) {
                            // Opponent accepts
                            System.out.println("Opponent accepts!");
                            matchesRef.child(matchID).removeEventListener(responseListener);
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    lobby.startGame(match, true);
                                }
                            });
                        } else {
                            // Invalid level
                            System.out.println("Error: Invalid level");
                        }
                    } else {
                        System.out.println("Initializing responseListener");
                    }
                } else {
                    // Opponent declines
                    System.out.println("Opponent declines!");
                    lobby.challengeDeclined();
                    matchesRef.child(matchID).removeEventListener(responseListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        matchesRef.child(matchID).addValueEventListener(responseListener);
    }

    /**
     * Method for acepting the challenge by setting the value of Match to a match object
     * @param match
     */
    @Override
    public void acceptChallenge(Match match) {
        System.out.println("ACCEPT!");
        matchesRef.child(match.getMatchID()).setValue(match);
    }


    /**
     * Called when the player presses the "play" button in the DrawView in multiplayer.
     * It adds a listener that listens for a changed opponent drawing.
     * In the case that you are last, the onDataChange in the listener will be fired at once.
     * @param game
     */
    @Override
    public void ready(final Game game) {
        // Reference to match
        DatabaseReference matchRef = matchesRef.child(game.getMatch().getMatchID());

        // Drawing references
        final DatabaseReference playerDrawingRef, opponentDrawingRef;
//        if (game.getPlayer().getUser().isChallenger()) {
        if (game.playerIsChallenger()) {
            playerDrawingRef = matchRef.child("challengerDrawing");
            opponentDrawingRef = matchRef.child("challengedDrawing");
        } else {
            opponentDrawingRef = matchRef.child("challengerDrawing");
            playerDrawingRef = matchRef.child("challengedDrawing");
        }

        // Upload drawing
        playerDrawingRef.setValue(game.getPlayer().getDrawing().serializeLines());

        // Listen for opponent's drawing
        drawingListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<List<String>>> genericTypeIndicator = new GenericTypeIndicator<List<List<String>>>() {};
                final List<List<String>> opponentDrawing = dataSnapshot.getValue(genericTypeIndicator);
                if (opponentDrawing != null) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            opponentDrawingRef.removeEventListener(drawingListener);
                            game.receivedDrawing(opponentDrawing);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        opponentDrawingRef.addValueEventListener(drawingListener);
    }

    @Override
    public void exitMatch(final Game game) {
        DatabaseReference matchRef = matchesRef.child(game.getMatch().getMatchID());
        matchRef.removeValue();
    }

    @Override
    public void checkVersion() {
        FirebaseDatabase.getInstance().getReference("minimumVersionCode").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int minimumVersionCode = dataSnapshot.getValue(int.class);
                if (BuildConfig.VERSION_CODE > minimumVersionCode) {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            ScreenManager.getInstance().showScreen(ScreenEnum.INVALID_VERSION);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void startGame(User user) {
        user.setChallenge(null);
        user.setStatus("inGame");
        lobbyRef.child(user.getFBID()).setValue(user);
    }

    @Override
    public void uploadLevels(List<LevelData> levels) {
        DatabaseReference levelsRef = FirebaseDatabase.getInstance().getReference("levels");
        levelsRef.setValue(levels);
    }

    /**
     * @param match
     * @param challenged
     *
     * Is used both when declining and canceling a challenge.
     */
    @Override
    public void declineChallenge(Match match, User challenged) {
        if (responseListener != null) {
            // Removes response listener if canceled by challenger
            matchesRef.child(match.getMatchID()).removeEventListener(responseListener);
        }
        System.out.println("DECLINE!");
        // Remove match.
        matchesRef.child(match.getMatchID()).removeValue();

        // Update opponent: Set opponent to ready
        lobbyRef.child(match.getMatchID()).child("status").setValue("ready");

        // Update player: Remove challenger and set ready to true.

        challenged.setChallenge(null);
        challenged.setStatus("ready");

        lobbyRef.child(challenged.getFBID()).setValue(challenged);
    }
}
