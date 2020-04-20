package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.models.LeaderBoardEntry;
import com.mygdx.hastypastry.models.User;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.StyledTextButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MultiPlayerHighScoreView extends BaseView {
    private Game game;
    private MenuButton menuButton;
    protected BitmapFont font;
    protected BitmapFont roundFont;
    protected BitmapFont leaderboardFont;
    private Table table;
    protected Label leaderboardLabel;
    protected Label levelLabel;
    protected Label roundScoreLabel;
    //protected Label tempLabel;
    protected DecimalFormat df = new DecimalFormat("###.##");
    private ArrayList<LeaderBoardEntry> leaderBoard;
    private ArrayList<Label> labelArray;
    private int tableCounter;

    public MultiPlayerHighScoreView(Game game) {
        super();
        this.game = game;
        this.leaderBoard = game.getLeaderBoard().getLeaderBoard();
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        leaderboardLabel.setText("Leaderboard");
        levelLabel.setText(game.getLevel().getLevel());

        if (game.getPlayer().getNewLevelTime() == 0.0f || game.getResult() == "You lost!") {
            roundScoreLabel.setText("This round: DNF");
        } else {
            roundScoreLabel.setText("This round: " + df.format(game.getPlayer().getNewLevelTime()));
        }
    }

    @Override
    public void buildStage() {
        // Creating menu button.
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 220);

        // Add button to the stage
        this.ui.addActor(menuButton);

        font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        roundFont = generateFont("pixelfont.TTF", 20);
        Label.LabelStyle roundLabelStyle = new Label.LabelStyle(roundFont, Color.BLACK);

        leaderboardFont = generateFont("pixelfont.TTF", 24);
        Label.LabelStyle leaderboardLabelStyle = new Label.LabelStyle(leaderboardFont, Color.BLACK);

        leaderboardLabel = new Label("High Score", labelStyle);
        levelLabel = new Label("LevelID", labelStyle);
        roundScoreLabel = new Label("This round:", roundLabelStyle);

        labelArray = new ArrayList<>();

        if(!leaderBoard.isEmpty()) {
            tableCounter = 1;
            labelArray.clear();
            for (LeaderBoardEntry entry : leaderBoard) {
                labelArray.add(new Label((tableCounter) + "  " + entry.getName() + " " + df.format(entry.getTime()), leaderboardLabelStyle));
                tableCounter++;

            }
        }

        table = new Table();
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(leaderboardLabel);
        table.row();
        table.add(levelLabel).padBottom(50);
        table.row();
        if(!labelArray.isEmpty()) {
            for (int i = 0; i<5; i++) {
                table.add(labelArray.get(i)).padTop(10);
                table.row();
            }
        }
        table.add(roundScoreLabel).padTop(50);

        this.ui.addActor(table);

        if (!game.getResult().equals("Oh no!")) {
            // If result is 'Oh no!' it means your opponent left
            final StyledTextButton newRoundBtn = new StyledTextButton("New Round");
            newRoundBtn.setPosition(Config.UI_WIDTH/2 - newRoundBtn.getWidth()/2, Config.UI_HEIGHT/2 - 290);
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
