package com.mygdx.hastypastry.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.hastypastry.HastyPastryGame;

public class MenuState extends GameState {
    private Texture background;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        cam.setToOrtho(false, HastyPastryGame.WIDTH, HastyPastryGame.HEIGHT);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0, HastyPastryGame.WIDTH, HastyPastryGame.HEIGHT);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
