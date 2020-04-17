package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.models.User;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.ui.StyledTextButton;
import com.mygdx.hastypastry.ui.MenuButton;

public class CompletedMultiplayerView extends BaseView {
    private Game game;

    public CompletedMultiplayerView(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void buildStage() {
        // Sound effects
        if(PlayerPreferences.instance.isMusicEnabled()) {
            MusicAndSound.instance.getGameMusic().setVolume(PlayerPreferences.instance.getMusicVolume());
        }

        game.getLobby().initCompleteMultiplayerView(ui);

        // Create menu button
        MenuButton menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2, Config.UI_HEIGHT/2, Align.center);
        ui.addActor(menuButton);

        if (!game.getResult().equals("Oh no!")) {
            // If result is 'Oh no!' it means your opponent left
            final StyledTextButton newRoundBtn = new StyledTextButton("New Round");
            newRoundBtn.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println(game.getOpponentUser().getFBID() + "    " + game.getOpponentUser());
                    for (User u : game.getLobby().getLobbyList()) {
                        System.out.println(u.getFBID() + "    " + u);
                    }
                    if (game.getLobby().lobbyListContains(game.getOpponentUser())) {
                        game.getLobby().challengeUser(game.getOpponentUser());
                    } else {
                        newRoundBtn.remove();
                    }
                    return false;
                }
            });
            // Add button to the stage
            ui.addActor(newRoundBtn);
        }

        // Set up font and label style for result
        BitmapFont resultFont = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle resultLabelStyle = new Label.LabelStyle(resultFont, Color.BLACK);

        // Creates the label
        Label resultLabel = new Label(game.getResult(), resultLabelStyle);

        // Set up font and label style for message
        BitmapFont messageFont = generateFont("pixelfont.TTF", 16);
        Label.LabelStyle messageLabelStyle = new Label.LabelStyle(messageFont, Color.BLACK);
        Label messageLabel = new Label(game.getMessage(), messageLabelStyle);

        // Creates table
        Table table = new Table();
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(resultLabel).padBottom(40);
        table.row();
        table.add(messageLabel);

        // Adds table to ui
        this.ui.addActor(table);
    }
}
