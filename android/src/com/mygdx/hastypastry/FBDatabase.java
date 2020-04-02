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
import com.mygdx.hastypastry.levels.Level;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.User;
import com.mygdx.hastypastry.singletons.ScreenManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FBDatabase implements HastyPastryDatabase {
    private ChildEventListener lobbyListener;
    private final DatabaseReference lobbyRef = FirebaseDatabase.getInstance().getReference("lobby");

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

    @Override
    public void joinLobby(final Lobby lobby, User user) {
        DatabaseReference userRef = lobbyRef.push();
        userRef.setValue(user);
        userRef.child("challenger").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String opponentName = (String)dataSnapshot.getValue();
                if (opponentName != "") {
                    Gdx.app.postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            lobby.startGame(opponentName);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        user.setFBID(userRef.getKey());
    }

    public void exitLobby(String FBID) {
        lobbyRef.removeEventListener(lobbyListener);
        lobbyRef.child(FBID).removeValue();
    }

    @Override
    public void challangePlayer(Lobby lobby, String opponentFBID, String playerName) {
        lobbyRef.child(opponentFBID).child("challenger").setValue(playerName);
        lobby.startGame("Per");
    }
}
