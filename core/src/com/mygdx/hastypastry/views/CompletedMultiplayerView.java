package com.mygdx.hastypastry.views;

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
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.singletons.DBManager;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.LabelButton;
import com.mygdx.hastypastry.ui.MenuButton;

public class CompletedMultiplayerView extends BaseView {
    private Game game;

    public CompletedMultiplayerView(Game game) {
        this.game = game;
    }

    @Override
    public void buildStage() {
        game.getLobby().initCompleteMultiplayerView(ui);

        // Create menu button
        MenuButton menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2, Config.UI_HEIGHT/2, Align.center);

        LabelButton newRoundBtn = new LabelButton("New Round");
        newRoundBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getLobby().challengeUser(game.getOpponentUser());
                return false;
            }
        });
        // Add button to the stage
        this.ui.addActor(newRoundBtn);
        this.ui.addActor(menuButton);

        // Set up font and label style
        BitmapFont font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        // Set text.
        String text = game.getWinner() == "player" ? "You win!" : "You lost!";
        // Creates the label
        Label label = new Label(text, labelStyle);

        // Creates table
        Table table = new Table();
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(label);

        // Adds table to ui
        this.ui.addActor(table);
    }
}
