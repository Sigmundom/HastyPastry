package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.MenuButton;

public class CompletedLevelView extends BaseView {

    private MenuButton menuButton;
    private BitmapFont font = new BitmapFont();

    public CompletedLevelView() { super(); }

    @Override
    public void buildStage() {
        // Create button
        menuButton = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2, Config.UI_HEIGHT/2 - 200);

        // Add button to the stage
        this.ui.addActor(menuButton);
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
