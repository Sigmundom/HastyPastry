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

public class HighScoreListView extends BaseView {
    private Game game;
    private MenuButton menuButton;
    protected BitmapFont font;
    private Table table;
    protected Label titleLabel;
    protected Label timeLabel;
    protected float levelTime;
    protected DecimalFormat df = new DecimalFormat("###.##");

    public HighScoreListView(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        levelTime = (float) game.getPlayer().getNewLevelTime().get(game.getPlayer().getNewLevelTime().size() - 1);
        titleLabel.setText("HIGH SCORES");
        System.out.println(levelTime);
        // timeLabel.setText("Level: " + levelTime.get(0) + "\nTime: " + df.format(levelTime.get(1)));
        timeLabel.setText("Time: " + df.format(levelTime));
    }

    @Override
    public void buildStage() {
        // Creating menu button.
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 300);

        // Add button to the stage
        this.ui.addActor(menuButton);

        font = generateFont("pixelfont.TTF", 24);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        titleLabel = new Label("Title", labelStyle);
        timeLabel = new Label("Time", labelStyle);
        table = new Table();
        table.top().padLeft(Config.UI_WIDTH/2 - 100).left().padTop(10);
        table.setFillParent(true);
        table.add(titleLabel);
        table.row();
        table.add(timeLabel);

        this.ui.addActor(table);

    }
}
