package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.ui.MenuButton;

public class FailedLevelView extends BaseView {

    private MenuButton menuButton;
    private BitmapFont font = new BitmapFont();

    public FailedLevelView() { super(); }

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
        String text = "Level failed. Try again!\n";
        GlyphLayout gl1 = new GlyphLayout();
        gl1.setText(font, text);

        // Tips
        String tips = "Tips: \nDon't hit the red obstacles. \nThey are deadly!";
        GlyphLayout gl2 = new GlyphLayout();
        gl2.setText(font, tips);



        // Draw the text
        font.draw(batch, text, Config.WORLD_WIDTH/2 - gl1.width/2, Config.WORLD_HEIGHT/2);
        font.draw(batch, tips, Config.WORLD_WIDTH/2 - gl2.width/2, Config.WORLD_HEIGHT/2 - 2);
    }
}
