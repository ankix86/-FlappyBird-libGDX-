package com.dexter.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dexter.game.state.GameStateManager;
import com.dexter.game.state.menuState;


public class game extends ApplicationAdapter {
    SpriteBatch batch;
    public static final int HEIGHT = 580;
    public static final int WIDTH = 340;
    public static final String TITLE = "Flappy Bird";
    public static float endTime;
    private int score = 0;
    public boolean gameOver;
    public GameStateManager gsm;

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.endTime = 0f;
        gsm = new GameStateManager();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        gsm.push(new menuState(gsm));
    }

    @Override
    public void render() {
        //Infinity Loop

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
