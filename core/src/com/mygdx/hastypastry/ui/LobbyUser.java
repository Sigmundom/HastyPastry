package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.mygdx.hastypastry.listeners.MyButtonListener;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.User;

public class LobbyUser extends Container<StyledTextButton> {

    public LobbyUser(final Lobby lobby, final User user) {
        super();
        StyledTextButton button = new StyledTextButton(user.getName());
        button.addListener(new MyButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                lobby.challengeUser(user);
            }
        });
        button.setDisabled(user.getStatus().equals("busy"));
        this.setActor(button);
        this.width(200);
        user.setUserUI(this);
    }
}
