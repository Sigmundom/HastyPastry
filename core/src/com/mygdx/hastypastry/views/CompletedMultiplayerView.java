package com.mygdx.hastypastry.views;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.LeaderBoard;
import com.mygdx.hastypastry.models.dbmodels.User;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.StyledTextButton;

import java.text.DecimalFormat;

public class CompletedMultiplayerView extends AbstractView {
    private Game game;
    protected DecimalFormat df = new DecimalFormat("###.##");
    private Label playerLabel, playerTimeLabel, opponentLabel, opponentTimeLabel;
    private LeaderBoard leaderBoard;
    private Sound buttonSound;

    public CompletedMultiplayerView(Game game) {
        super();
        this.game = game;
        this.leaderBoard = game.getLeaderBoard();
        buttonSound = MusicAndSound.instance.getButtonSound();
    }

    @Override
    public void buildStage() {
        // Sound effects
        if (PlayerPreferences.instance.isMusicEnabled()) {
            MusicAndSound.instance.getGameMusic().setVolume(PlayerPreferences.instance.getMusicVolume());
        }

        game.getLobby().initCompleteMultiplayerView(ui);

        // Set up font and label style for result
        BitmapFont resultFont = generateFont("pixelfont.TTF", 30);
        Label.LabelStyle resultLabelStyle = new Label.LabelStyle(resultFont, Color.BLACK);

        // Creates the label
        Label resultLabel = new Label(game.getResult(), resultLabelStyle);

        // Set up font and label style for message
        BitmapFont messageFont = generateFont("pixelfont.TTF", 16);
        Label.LabelStyle messageLabelStyle = new Label.LabelStyle(messageFont, Color.BLACK);
        Label messageLabel = new Label(game.getMessage(), messageLabelStyle);

        // Viewing high scores for player and opponent, and sending it to Firebase
        if (game.getPlayer().getNewLevelTime() == 0.0f || game.getResult() == "You lost!") {
            playerLabel = new Label(game.getPlayer().getName() + ":", resultLabelStyle);
            playerTimeLabel = new Label("DNF", resultLabelStyle);
        } else {
            playerLabel = new Label(game.getPlayer().getName() + ":", resultLabelStyle);
            playerTimeLabel = new Label(df.format(game.getPlayer().getNewLevelTime()), resultLabelStyle);
        }
        if (game.getOpponent().getNewLevelTime() == 0.0f || game.getResult() == "You won!") {
            opponentLabel = new Label(game.getOpponent().getName() + ":", resultLabelStyle);
            opponentTimeLabel = new Label("DNF", resultLabelStyle);
        }

        // Creates table
        Table table = new Table();
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(resultLabel).padBottom(40);
        table.row();
        table.add(messageLabel);
        table.row();

        table.add(resultLabel);
        table.row();
        table.add(playerLabel).padTop(50);
        table.row();
        table.add(playerTimeLabel).padTop(10);
        table.row();
        table.add(opponentLabel).padTop(30);
        table.row();
        table.add(opponentTimeLabel).padTop(10);

        // Adds table to ui
        this.ui.addActor(table);

        // Creating high score button, sending game through to high score list.
        StyledTextButton highScoreButton = new StyledTextButton("  High Score  ");

        highScoreButton.setPosition(Config.UI_WIDTH / 2 - highScoreButton.getWidth() / 2 + 90, Config.UI_HEIGHT / 2 - 310);
        highScoreButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (PlayerPreferences.instance.isSoundEffectsEnabled()) {
                            buttonSound.play(0.5f);
                        }
                        ScreenManager.getInstance().showScreen(ScreenEnum.LEADERBOARD, game);
                        return false;
                    }
                });
        this.ui.addActor(highScoreButton);

        // Create go to lobby button
        MenuButton lobbyButton = new MenuButton("Lobby", ScreenEnum.LOBBY, game.getLobby());
        lobbyButton.setWidth(highScoreButton.getWidth());
        lobbyButton.setPosition(Config.UI_WIDTH/2-90, Config.UI_HEIGHT / 2 -220, Align.center);
        ui.addActor(lobbyButton);

        // Create menu button
        MenuButton menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setWidth(highScoreButton.getWidth());
        menuButton.setPosition(Config.UI_WIDTH / 2 + 90, Config.UI_HEIGHT / 2 - 220, Align.center);
        ui.addActor(menuButton);

        if (!game.getResult().equals("Oh no!")) {
            // If result is 'Oh no!' it means your opponent left
            final StyledTextButton newRoundBtn = new StyledTextButton("New Round");
            newRoundBtn.setWidth(highScoreButton.getWidth());
            newRoundBtn.setPosition(Config.UI_WIDTH / 2 - newRoundBtn.getWidth() / 2 - 90, Config.UI_HEIGHT / 2 - 310);
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
    }
}
