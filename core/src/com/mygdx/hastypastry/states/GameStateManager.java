package com.mygdx.hastypastry.states;

import java.util.Stack;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameStateManager {
    private Stack<GameState> states;

    public GameStateManager(){
        states = new Stack<GameState>();
    }

    public void push(GameState state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(GameState state){
        states.push(state);
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
