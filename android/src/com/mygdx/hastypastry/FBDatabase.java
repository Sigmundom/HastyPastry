package com.mygdx.hastypastry;

import com.badlogic.gdx.Gdx;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.interfaces.HastyPastryDatabase;
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
    private DatabaseReference matchRef;
    private User user;
    private Lobby lobby;

    /**
     * Called from lobby-class during initialisation.
     * Makes sure that Lobby-Class is updated on changes of Lobby participants, challenges, and matches.
     * @param lobby
     */
    public void subscribeLobbyList(final Lobby lobby) {
        this.lobby = lobby;
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
     * @param user
     */
    @Override
    public void joinLobby(User user) {
        this.user = user;
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
     */
    public void exitLobby() {
        if (user != null) {
            // Remove the user from lobby and possible hosted matches
            lobbyRef.child(user.getFBID()).removeValue();
            matchesRef.child(user.getFBID()).removeValue();

            if (challengeListener != null) {
                lobbyRef.child(user.getFBID()).child("challenge").removeEventListener(challengeListener);
            }
        }
        exitMatch();
        if (lobbyListener != null) {
            lobbyRef.removeEventListener(lobbyListener);
        }
        user = null;
        lobby = null;
    }


    /**
     * Used by Lobby class to pose a challenge to another player
     * Will thereafter wait for a response, by listening for changes to the value.
     * Depending of the actions of the opponent, the challenged can be denied or accepted.
     * In our case, if the level is set by the opponent, the challenge is accepted
     * If the opponent deletes the match, it is denied.
     * @param opponent
     */
    @Override
    public void challengePlayer(User opponent) {
        exitMatch(); // Clean-up after previous match if any.
        matchRef = matchesRef.push();
        Match match = new Match(matchRef.getKey(), user, opponent);
        // Creates a new match and adds it to firebase.
        matchRef.setValue(match);

        // Setting player's ready field to false to prevent new challenges.
        lobbyRef.child(user.getFBID()).child("status").setValue("busy");

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
                            matchRef.removeEventListener(responseListener);
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
                    matchRef.removeEventListener(responseListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        matchRef.addValueEventListener(responseListener);
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
        // Define database references
        DatabaseReference matchRef = matchesRef.child(game.getMatch().getMatchID());
        DatabaseReference playerDrawingRef = game.playerIsChallenger() ? matchRef.child("challengerDrawing") : matchRef.child("challengedDrawing");

        // Upload drawing
        playerDrawingRef.setValue(game.getPlayer().getDrawing().serializeLines());

        // Listen for opponent's drawing
    }

    @Override
    public void exitMatch() {
        if (matchRef != null) {
            matchRef.removeValue();
            if (drawingListener != null) {
                matchRef.removeEventListener(drawingListener);
            }
        }
        matchRef = null;
        drawingListener = null;
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
    public void withdrawChallenge(User opponent) {
        //Remove response listener
        matchRef.removeEventListener(responseListener);

        // Remove match
        matchRef.removeValue();

        // Update user: Set status to ready
        lobbyRef.child(user.getFBID()).child("status").setValue("ready");

        // Update opponent: Remove challenge and set ready to true.
        opponent.setChallenge(null);
        opponent.setStatus("ready");
        lobby.setEnabledUserUI(opponent, false);

        lobbyRef.child(opponent.getFBID()).setValue(opponent);
    }

    @Override
    public void startGame(final Game game) {
        // Update user status in lobby
        user.setChallenge(null);
        user.setStatus("inGame");
        lobbyRef.child(user.getFBID()).setValue(user);

        // Listen for when opponent is ready
        matchRef = matchesRef.child(game.getMatch().getMatchID());
        drawingListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Receives an updated match object
                final Match updatedMatch = dataSnapshot.getValue(Match.class);

                if (updatedMatch != null) {
                    // Tries to retrieve the opponents drawing
                    final List<List<String>> opponentDrawing;
                    if (game.playerIsChallenger()) {
                        opponentDrawing = updatedMatch.getChallengedDrawing();
                    } else {
                        opponentDrawing = updatedMatch.getChallengerDrawing();
                    }

                    if (opponentDrawing != null) {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                game.receivedDrawing(opponentDrawing);
                            }
                        });
                    }
                } else if (game.getResult().equals("") && !game.getOpponent().getWaffle().isDead()){
                    // Match doesn't exist in FB, no result and opponent hasn't died.
                    // It should mean that your opponent left the game.
                    // Clean-up and end game
                    exitMatch();
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            game.setResult("Oh no!");
                            game.setMessage("Your opponent left!");
                            game.gameOver();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        matchRef.addValueEventListener(drawingListener);
    }

    /**
     * @param match
     *
     * Is used both when declining and canceling a challenge.
     */
    @Override
    public void declineChallenge(Match match) {
        System.out.println("DECLINE!");
        // Remove match.
        matchesRef.child(match.getMatchID()).removeValue();

        // Update opponent: Set opponent to ready
        lobbyRef.child(match.getChallenger().getFBID()).child("status").setValue("ready");

        // Update player: Remove challenger and set ready to true.

        user.setChallenge(null);
        user.setStatus("ready");

        lobbyRef.child(user.getFBID()).setValue(user);
    }
}
