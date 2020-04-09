package com.mygdx.hastypastry.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class MyContactListener implements ContactListener {
    private Game game;

    public MyContactListener(Game game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {
        String a = contact.getFixtureA().getBody().getUserData().toString();
        String b = contact.getFixtureB().getBody().getUserData().toString();
        System.out.println(a + " + " + b);
        if (a.equals("goal") || b.equals("goal")) {
            // Game finished
            if (game.isMultiplayer()) {
                // Set winner
                if (a.equals("playerWaffle") || b.equals("playerWaffle")) {
                    game.setWinner("player");
                } else {
                    game.setWinner("opponent");
                }
                // Go to Completed multiplayer screen
                System.out.println(game.getWinner());
                ScreenManager.getInstance().showScreen(ScreenEnum.COMPLETED_MULTIPLAYER, game);
            } else { // Single player
                // Go to Completed level screen
                ScreenManager.getInstance().showScreen(ScreenEnum.COMPLETED_LEVEL, game);
            }

        }
        if (a.equals("deadly") || b.equals("deadly")) {
            ScreenManager.getInstance().showScreen(ScreenEnum.FAILED_lEVEL);
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
