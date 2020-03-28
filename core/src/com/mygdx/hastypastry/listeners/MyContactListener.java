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
        if (((contact.getFixtureA().getBody().getUserData() == "waffle") && (contact.getFixtureB().getBody().getUserData() == "goal")) || ((contact.getFixtureB().getBody().getUserData() == "waffle") && (contact.getFixtureA().getBody().getUserData() == "goal"))) {
            ScreenManager.getInstance().showScreen(ScreenEnum.COMPLETED_LEVEL, game);
        }
        if (((contact.getFixtureA().getBody().getUserData() == "waffle") && (contact.getFixtureB().getBody().getUserData() == "deadly")) || ((contact.getFixtureB().getBody().getUserData() == "waffle") && (contact.getFixtureA().getBody().getUserData() == "deadly"))) {
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
