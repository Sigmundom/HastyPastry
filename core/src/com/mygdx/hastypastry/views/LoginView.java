package com.mygdx.hastypastry.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.hastypastry.Config;
import com.mygdx.hastypastry.controllers.PlayerPreferences;
import com.mygdx.hastypastry.enums.ScreenEnum;
import com.mygdx.hastypastry.models.Lobby;
import com.mygdx.hastypastry.singletons.Assets;
import com.mygdx.hastypastry.singletons.MusicAndSound;
import com.mygdx.hastypastry.singletons.ScreenManager;
import com.mygdx.hastypastry.ui.StyledTextButton;
import com.mygdx.hastypastry.ui.MenuButton;

public class LoginView extends BaseView {
    private TextField input;
    private Label error;
    private Lobby lobby;
    private PlayerPreferences playerPreferences;
    private Sound buttonSound;

    public LoginView() {
        super();
        lobby = new Lobby();
        playerPreferences = new PlayerPreferences();
        buttonSound = MusicAndSound.instance.getButtonSound();
    }

    @Override
    public void buildStage() {
        // Creates and adds the Menu button to ui.
        MenuButton menuBtn = new MenuButton("Menu", ScreenEnum.MAIN_MENU);
        menuBtn.setPosition(10, Config.UI_HEIGHT - menuBtn.getHeight() - 10);
        ui.addActor(menuBtn);

        // Set up font and label style
        BitmapFont font = generateFont("pixelfont.TTF", 32);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);

        // Creates the label
        Label label = new Label("Multiplayer", labelStyle);
//        label.setText("Multiplayer");
        label.setPosition(Config.UI_WIDTH/2, Config.UI_HEIGHT + 100);

        // Creates the input field.
        input = new TextField("", Assets.instance.getManager().get(Assets.orangeUiSkin), "login");
        input.setMessageText("Write your name here");
        input.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                // Join lobby on Enter/Line-break
                if (c == '\n') {
                   goToLobby();
                }
                error.setText("");
            }
        });

        // Creates error label
        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.RED);
        error = new Label("", style);

        // Creates the submit button
        StyledTextButton submitBtn = new StyledTextButton("Join Lobby");
        submitBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                goToLobby();
                if(playerPreferences.isSoundEffectsEnabled()) {
                    buttonSound.play(0.5f);
                }
                return false;
            }
        });

        // Creates a table and add elements
        Table table = new Table();
        table.top().padTop(150);
        table.setFillParent(true);
        table.add(label).padBottom(40);
        table.row();
        table.add(input).width(250).height(50).padBottom(10);
        table.row();
        table.add(error).padBottom(10);
        table.row();
        table.add(submitBtn);


        ui.addActor(table);
    }

    private void goToLobby() {
        String name = input.getText().trim();
        if (name.length() < 4) {
            error.setText("Your name have at least 4 characters!");
        } else {
            if (lobby.isNameTaken(name)) {
                error.setText("Your name is already taken!");
            } else {
                lobby.joinLobby(name);
                ScreenManager.getInstance().showScreen(ScreenEnum.LOBBY, lobby);
            }
        }
    }

    @Override
    public void hide() {
        Gdx.input.setOnscreenKeyboardVisible(false);
    }
}
