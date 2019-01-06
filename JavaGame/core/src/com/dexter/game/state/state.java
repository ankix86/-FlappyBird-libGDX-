package com.dexter.game.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class state {
    protected OrthographicCamera cam;
    protected GameStateManager gsm;

    public state(GameStateManager gsm) {
        this.cam = new OrthographicCamera();
        this.gsm = gsm;
    }

    public abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);
}
