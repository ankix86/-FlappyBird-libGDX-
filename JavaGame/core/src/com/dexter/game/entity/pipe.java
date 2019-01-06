package com.dexter.game.entity;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import com.dexter.game.game;
import com.dexter.game.spritesSheet.sprites;

public class pipe {

    private static final float FLUCTUATION = 200;
    private static final float GAP = 100;
    private static final float LOWEAST_OPENING = 215;
    public static float movement = -2.5f;
    private Random rand;
    private sprites pipeButtomTex, pipeTopTex, groundTex;
    public Vector2[] groundPos = new Vector2[3];
    public Vector2[] topPipePoss = new Vector2[3];
    public Vector2[] buttomPipePoss = new Vector2[topPipePoss.length];

    public pipe() {
        this.rand = new Random();
        this.pipeButtomTex = new sprites("pipe-green");
        this.pipeTopTex = new sprites("pipe-green");
        pipeTopTex.getSprite().flip(false, true);
        this.groundTex = new sprites("base");

        for (int i = 0; i < groundPos.length; i++) {
            float gap = i * this.groundTex.getSprite().getWidth();
            this.groundPos[i] = new Vector2(gap, this.groundTex.getSprite().getHeight() - this.groundTex.getSprite().getHeight());
        }
        for (int i = 0; i < topPipePoss.length; i++) {
            float gap = i * pipeTopTex.getSprite().getWidth() * 3 + game.WIDTH / 2 + 500;
            this.topPipePoss[i] = new Vector2(gap, rand.nextFloat() * FLUCTUATION + GAP + LOWEAST_OPENING);
            this.buttomPipePoss[i] = new Vector2(gap, this.topPipePoss[i].y - GAP - this.pipeTopTex.getSprite().getHeight());
        }
    }

    public void pipePositioning() {
        for (int i = 0; i < topPipePoss.length; i++) {
            if (this.topPipePoss[i].x <= -pipeTopTex.getSprite().getWidth()) {
                this.topPipePoss[i].x = game.WIDTH + pipeTopTex.getSprite().getWidth() * 2 - 28;
                this.topPipePoss[i].y = rand.nextFloat() * FLUCTUATION + GAP + LOWEAST_OPENING;
                this.buttomPipePoss[i].y = this.topPipePoss[i].y - GAP - this.pipeTopTex.getSprite().getHeight();
            }
        }
    }

    public void movement(float dt) {
        //pips
        for (int i = 0; i < topPipePoss.length; i++) {
            this.buttomPipePoss[i].x = this.topPipePoss[i].x;
            this.topPipePoss[i].add(movement, 0);
            this.topPipePoss[i].scl(dt);
            this.topPipePoss[i].scl(1 / dt);
        }
    }

    public void groundMovement() {
        for (int i = 0; i < this.groundPos.length; i++) {
            this.groundPos[i].add(movement, 0);

            if (this.groundPos[i].x < -groundTex.getSprite().getWidth()) {
                this.groundPos[i].x = game.WIDTH - 25;
            }
        }
    }

    public Vector2[] getTopPipePos() {
        return topPipePoss;
    }

    public Vector2[] getButtomPipePos() {
        return buttomPipePoss;
    }

    public Vector2[] getGroundPos() {
        return groundPos;
    }

    public Sprite getGroundTex() {
        return groundTex.getSprite();
    }

    public Sprite getTopPipeTex() {
        return pipeTopTex.getSprite();
    }

    public Sprite getButtomPipeTex() {
        return pipeButtomTex.getSprite();
    }

}
