package com.mygdx.hastypastry.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Game;
import com.mygdx.hastypastry.singletons.PlayerPreferences;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.MenuButton;
import com.mygdx.hastypastry.ui.StyledTextButton;

import java.text.DecimalFormat;

public class SinglePlayerHighScoreView extends BaseView {
    private Game game;
    private Label highscoreLabel;
    private Label levelLabel;
    private Label personalHighScoreLabel;
    private float levelTime;
    private DecimalFormat df = new DecimalFormat("###.##");
    private Texture texture = new Texture("star.png");
    private Sprite sprite = new Sprite(texture);


    public SinglePlayerHighScoreView(Game game) {
        super();
        this.game = game;
        sprite.setSize(Config.UI_WIDTH/80, Config.UI_WIDTH/80);
        sprite.setOrigin(sprite.getWidth()/2,-15);
    }

    @Override
    public void draw(SpriteBatch batch, float delta) {
        float rotationDegree = 15.0f;
        for(float ranking : game.getLevel().getStarRank()) {
            if(levelTime < ranking) {
                sprite.draw(batch);
                sprite.setPosition(Config.WORLD_WIDTH/2 - sprite.getWidth()/2, Config.WORLD_HEIGHT/2);
                sprite.setRotation(rotationDegree);
                rotationDegree -= 15.0f;
            }
        }
        batch.end();

        highscoreLabel.setText("High Score");
        levelLabel.setText(game.getLevel().getLevel());

        PlayerPreferences.instance.setPrefHighScore(game);
        personalHighScoreLabel.setText("Best: " + df.format(PlayerPreferences.instance.getPersonalHighScore()));
        levelTime = PlayerPreferences.instance.getPersonalHighScore();
    }

    @Override
    public void buildStage() {
        // Creating menu button.
        MenuButton menuButton = new MenuButton("     Menu     ", ScreenEnum.MAIN_MENU);
        menuButton.setPosition(Config.UI_WIDTH/2 - menuButton.getWidth()/2 + 90, Config.UI_HEIGHT/2 - 300);

        // Creating back button.
        final StyledTextButton backBtn = new StyledTextButton("     Back     ");
        backBtn.setPosition(Config.UI_WIDTH/2 - backBtn.getWidth()/2 - 90, Config.UI_HEIGHT/2 - 300);
        backBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ScreenManager.getInstance().showScreen(ScreenEnum.COMPLETED_LEVEL, game);
                return false;
            }
        });
        // Add button to the stage
        ui.addActor(backBtn);

        // Add button to the stage
        this.ui.addActor(menuButton);

        BitmapFont font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        highscoreLabel = new Label("High Score", labelStyle);
        levelLabel = new Label("LevelID", labelStyle);
        personalHighScoreLabel = new Label("Personal High Score", labelStyle);
        Table table = new Table();
        table.top().padTop(30);
        table.setFillParent(true);
        table.add(highscoreLabel);
        table.row();
        table.add(levelLabel);
        table.row();
        table.add(personalHighScoreLabel).padTop(250);

        this.ui.addActor(table);

    }
}
