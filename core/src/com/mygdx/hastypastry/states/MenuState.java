package com.mygdx.hastypastry.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.hastypastry.HastyPastryGame;
import com.mygdx.hastypastry.models.RoundObstacle;

public class MenuState extends GameState {
    private Texture background;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("bg.png");
        cam.setToOrtho(false, HastyPastryGame.WIDTH, HastyPastryGame.HEIGHT);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.push(new PlayState(gsm, 20, 20));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
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
        background.dispose();
    }
}
