package com.mygdx.hastypastry.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class MyContactListener implements ContactListener {
    private Game game;
    private Sound goalSound;
    private Sound gameoverSound;

    public MyContactListener(Game game) {
        this.game = game;
        goalSound = Gdx.audio.newSound(Gdx.files.internal("goal_pizzi.ogg"));
        gameoverSound = Gdx.audio.newSound(Gdx.files.internal(("gameover_pizzi.ogg")));
    }

    @Override
    public void beginContact(Contact contact) {
        String a = contact.getFixtureA().getBody().getUserData().toString();
        String b = contact.getFixtureB().getBody().getUserData().toString();
        if (a.equals("goal") || b.equals("goal")) {
            // Game finished
            goalSound.play(0.5f);
            if (game.isMultiplayer()) {
                // Set winner
                if (a.equals("playerWaffle") || b.equals("playerWaffle")) {
                    game.setResult("You won!");
                } else {
                    game.setResult("You lost!");
                }
                game.gameOver();
            } else { // Single player
                // Go to Completed level screen
                ScreenManager.getInstance().showScreen(ScreenEnum.COMPLETED_LEVEL, game);
            }

        }
        if (a.equals("deadly") || b.equals("deadly")) {
            gameoverSound.play(0.5f);
            if (game.isMultiplayer()) {
                if (a.equals("playerWaffle") || b.equals("playerWaffle")) {
                    game.playerDied("You hit a dangerous object!");
                } else if (a.equals("opponentWaffle") || b.equals("opponentWaffle")) {
                    game.opponentDied();
                }
            } else {
                game.setMessage("You hit a dangerous object!");
                game.gameOver();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
