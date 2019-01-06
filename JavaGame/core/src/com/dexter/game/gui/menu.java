package com.dexter.game.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.dexter.game.game;
import com.dexter.game.spritesSheet.sprites;

public class menu {
    private BitmapFont font;
    private sprites gameOver,progress,playButton,positionButton,touchToStart,Gcoin,Scoin;
    private float y,yVal;
    private Vector2 playButtonPos;
    private boolean gameOverPos = false;

    public menu(){
        yVal = 10;
        font = new BitmapFont(Gdx.files.internal("font\\my.fnt"));
        gameOver = new sprites("gameOver");
        progress = new sprites("progress");
        positionButton = new sprites("position");
        playButton = new sprites("playButton");
        Scoin = new sprites("sCoin");
        Gcoin = new sprites("gCoin");
        touchToStart = new sprites("touchToStart");
        //playBtnPos
        playButtonPos = new Vector2(30,  game.HEIGHT/4);
        y = -progress.getSprite().getHeight();
        //font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);

    }
    public void update(){
        System.out.println("Y :"+y);
        y += yVal;
        if(y < game.HEIGHT/2 - progress.getSprite().getHeight()/2){
            yVal = 10;
        }
        else{
            yVal = 0;
            gameOverPos = true;
        }
    }

    public Sprite getGameOver() {
        return gameOver.getSprite();
    }

    public Sprite getProgress() {
        return progress.getSprite();
    }

    public float getY() {
        return this.y;
    }

    public Sprite getPlayButton() {
        return playButton.getSprite();
    }

    public Sprite getPositionButton() {
        return positionButton.getSprite();
    }

    public Vector2 getPlayButtonPos() {
        return playButtonPos;
    }

    public boolean getGameOverPos(){
        return gameOverPos;
    }

    public  Sprite getTouchToStart(){ return touchToStart.getSprite(); }

    public Sprite getGcoin() {
        return Gcoin.getSprite();
    }

    public Sprite getScoin() {
        return Scoin.getSprite();
    }
    public void setGameOverPos(boolean set){
        gameOverPos = set;
    }

    public BitmapFont getFont() {
        return font;
    }
}

