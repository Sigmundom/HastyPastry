package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.HighScoreButton;
import com.mygdx.hastypastry.ui.MenuButton;

public class CompletedLevelView extends BaseView {
    private Game game;
    private MenuButton menuButton;
    private HighScoreButton highScoreButton;
    private BitmapFont font = new BitmapFont();

    public CompletedLevelView(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void buildStage() {
        // Create menu button
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 200);

        // Creating high score button, sending game through to high score list.
        highScoreButton = new HighScoreButton("High Score");
        highScoreButton.setPosition(Config.UI_WIDTH/2 - highScoreButton.getWidth()/2, Config.UI_HEIGHT/2 - 300);
        highScoreButton.addListener(
                new InputListener() {
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (game.isMultiplayer()) {
                            // game.getPlayer().getDrawing().uploadLines(game.getGameID(), game.getPlayer().getName());
                            ScreenManager.getInstance().showScreen(ScreenEnum.HIGHSCORE, game);
                        } else {
                            ScreenManager.getInstance().showScreen(ScreenEnum.HIGHSCORE, game);
                        }
                        return false;
                    }
                });

        // Add button to the stage
        this.ui.addActor(menuButton);
        this.ui.addActor(highScoreButton);
    }

    @Override
    public void draw(SpriteBatch batch, float delta){
        // Set font parameters
        font.setColor(Color.BLACK);
        font.getData().setScale(0.06f);
        font.setUseIntegerPositions(false);

        // Find width of text for centering
        String text = "You completed the level in " + "XX" + " seconds!";
        GlyphLayout gl = new GlyphLayout();
        gl.setText(font, text);

        // Draw the text
        font.draw(batch, text, Config.WORLD_WIDTH/2 - gl.width/2, Config.WORLD_HEIGHT/2);
    }
}
