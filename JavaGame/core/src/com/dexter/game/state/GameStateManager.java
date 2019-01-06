package com.dexter.game.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    Stack<com.dexter.game.state.state> state;

    public GameStateManager() {
        this.state = new Stack<com.dexter.game.state.state>();
    }

    public void push(state state) {
        this.state.push(state);
    }

    public void pop() {
        this.state.pop();
    }

    public void set(state state) {
        this.state.pop();
        this.state.push(state);
    }

    public void update(float dt) {
        this.state.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        this.state.peek().render(sb);
    }
}
