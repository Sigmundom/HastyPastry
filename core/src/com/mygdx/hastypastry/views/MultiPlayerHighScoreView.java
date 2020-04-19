package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.ui.MenuButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

public class MultiPlayerHighScoreView extends BaseView {
    private Game game;
    private MenuButton menuButton;
    protected BitmapFont font;
    protected BitmapFont leaderboardFont;
    private Table table;
    protected Label leaderboardLabel;
    protected Label levelLabel;
    protected Label roundScoreLabel;
    //protected Label tempLabel;
    protected DecimalFormat df = new DecimalFormat("###.##");
    private Map<String, Float> leaderBoard;
    private ArrayList<Label> labelArray;
    private int tableCounter;

    public MultiPlayerHighScoreView(Game game) {
        super();
        this.game = game;
        this.leaderBoard = game.getLeaderBoard().getLeaderBoard();
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        tableCounter = 1;

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
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 300);

        // Add button to the stage
        this.ui.addActor(menuButton);

        font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        leaderboardFont = generateFont("pixelfont.TTF", 20);
        Label.LabelStyle leaderboardLabelStyle = new Label.LabelStyle(leaderboardFont, Color.BLACK);

        leaderboardLabel = new Label("High Score", labelStyle);
        levelLabel = new Label("LevelID", labelStyle);
        roundScoreLabel = new Label("This round:", leaderboardLabelStyle);

        labelArray = new ArrayList<>();
        //tempLabel = new Label("", leaderboardLabelStyle);

        System.out.println("leaderboard is this big: " + leaderBoard.size());
        for(Map.Entry<String, Float> entry : leaderBoard.entrySet()) {
            // System.out.println(tableCounter + " " + entry.getKey());
            //tempLabel.setText((tableCounter) + "  " + entry.getKey() + " " + df.format(entry.getValue()));
            labelArray.add(new Label((tableCounter) + "  " + entry.getKey() + " " + df.format(entry.getValue()), leaderboardLabelStyle));
            System.out.println("labelarray is this big: " + labelArray.size());
            tableCounter++;
        }

        table = new Table();
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(leaderboardLabel);
        table.row();
        table.add(levelLabel);
        table.row();
        for(Label label : labelArray) {
            table.add(label).padTop(10);
            table.row();
        }
        table.add(roundScoreLabel).padTop(50);

        this.ui.addActor(table);

    }
}
