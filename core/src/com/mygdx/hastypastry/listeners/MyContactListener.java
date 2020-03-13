package com.mygdx.hastypastry.listeners;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.singletons.ScreenManager;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
//        System.out.println(contact.getFixtureA().getBody().getUserData());
//        System.out.println(contact.getFixtureB().getBody().getUserData());
        if ((boolean)contact.getFixtureA().getBody().getUserData() && (boolean)contact.getFixtureB().getBody().getUserData()) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
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
