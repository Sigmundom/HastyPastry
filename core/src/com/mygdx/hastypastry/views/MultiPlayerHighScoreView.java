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
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.StyledTextButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MultiPlayerHighScoreView extends AbstractView {
    private Game game;
    private MenuButton menuButton;
    protected BitmapFont font;
    protected BitmapFont leaderboardFont;
    private Table table;
    protected Label leaderboardLabel;
    protected Label levelLabel;
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
    }

    @Override
    public void buildStage() {
        // Creating menu button.
        menuButton = new MenuButton("     Menu     ", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 220);

        // Add button to the stage
        this.ui.addActor(menuButton);

        font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        leaderboardFont = generateFont("pixelfont.TTF", 24);
        Label.LabelStyle leaderboardLabelStyle = new Label.LabelStyle(leaderboardFont, Color.BLACK);

        leaderboardLabel = new Label("High Score", labelStyle);
        levelLabel = new Label("LevelID", labelStyle);

        labelArray = new ArrayList<>();

        // Writing the 7 best scores stored in the database leaderboard.
        if(!leaderBoard.isEmpty()) {
            System.out.println(leaderBoard.get(0).getName());
            tableCounter = 1;
            labelArray.clear();
            for (int i = 0 ; (i<7 && i<leaderBoard.size()) ; i++) {
                labelArray.add(new Label((tableCounter) + "  " + leaderBoard.get(i).getName() + " " + df.format(leaderBoard.get(i).getTime()), leaderboardLabelStyle));
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
            for (Label label : labelArray) {
                table.add(label).padTop(10);
                table.row();
            }
        }

        this.ui.addActor(table);

        // Adding back button.
        final StyledTextButton backBtn = new StyledTextButton("     Back     ");
        backBtn.setPosition(Config.UI_WIDTH/2 - backBtn.getWidth()/2, Config.UI_HEIGHT/2 - 290);
        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().showScreen(ScreenEnum.COMPLETED_MULTIPLAYER, game);
                return false;
            }
        });
        // Add button to the stage
        ui.addActor(backBtn);

    }
}
