package com.mygdx.hastypastry.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.models.Match;
import com.mygdx.hastypastry.singletons.Assets;

public class ChallengeBox extends Dialog {

    private final Lobby lobby;
    private Match match;


    public ChallengeBox(Lobby lobby, Match match) {
        super("Challenge!", Assets.instance.getManager().get(Assets.uiSkin), "dialog");
        this.lobby = lobby;
        this.match = match;
        this.text(match.getChallengerName() + " has challenged you!");
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(this.getSkin().get("container_blue", TextButton.TextButtonStyle.class));
        this.button("Accept", true, buttonStyle); //sends "true" as the result
        this.button("Decline", false, buttonStyle); //sends "false" as the result
        this.pad(40);
        this.padTop(60);
    }

    @Override
    public void result(Object obj) {
        if ((boolean)obj) {
            lobby.acceptChallenge(match);
        } else {
            lobby.declineChallenge(match);
        }
        hide();
    }
}
