package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.Match;
import com.mygdx.hastypastry.models.User;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.DBManager;

public class ChallengeBox extends Dialog {

    private final Lobby lobby;
    private User opponent;


    public ChallengeBox(Lobby lobby, User opponent) {
        super("Challenging " + opponent.getName() + "!", Assets.instance.getManager().get(Assets.orangeUiSkin), "dialog");
        this.lobby = lobby;
        this.opponent = opponent;
        this.text("Waiting for response...");
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(this.getSkin().get("default", TextButton.TextButtonStyle.class));
        this.button("Cancel", true, buttonStyle); //sends "true" as the result
    }

    @Override
    public void result(Object obj) {
        DBManager.instance.getDB().withdrawChallenge(opponent);
        hide();
    }
}
