package com.dexter.game.entity;

import com.dexter.game.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.dexter.game.spritesSheet.sprites;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class bird {
    private static final float GRAVITY = -16;
    private Vector2 position;
    private Vector2 velocity;
    public float rotation = 0, minAngle, maxAngle;
    public float rVal = 4;
    public float last_y_Pos = 0;
    private float y, yVal = 1;
    private Sound flap;
    public sprites sprite;
    private Random rand;
    private static boolean groundTouch;

    public bird() {
        rand = new Random();
        groundTouch = false;
        y = game.HEIGHT / 2;
        sprite = new sprites(3, 0.10f, "bird_" + rand.nextInt(2), true);
        this.velocity = new Vector2(0, 0);
        this.position = new Vector2(100, y);
        this.minAngle = -90;
        this.maxAngle = 30;
        this.flap = Gdx.audio.newSound(Gdx.files.internal("audio\\wing.ogg"));
    }

    public void update(float dt) {
        if (this.rotation < 0) {
            game.endTime = 1;
        }
        if (this.position.y >= 105) {
            this.velocity.add(0, this.GRAVITY);
            this.velocity.scl(dt);
            this.position.add(velocity.x, velocity.y);
            this.velocity.scl(1 / dt);
        }
        //rotation_logic
        if (this.position.y < this.last_y_Pos + 10) {
            rotation -= rVal;
            if (rotation < this.minAngle) {
                rotation = this.minAngle;
            }
        }
        collison();
    }

    public void collison() {
        if (this.position.y <= 105) {
            groundTouch = true;
            this.position.y = 105;
        }
        if (this.position.y >= game.HEIGHT - sprite.getTextureRegions()[0].getRegionHeight()) {
            this.position.y = game.HEIGHT - sprite.getTextureRegions()[0].getRegionHeight();
        }
    }

    public void lift() {
        //lift-logic
        this.velocity.y = 300;

        //track_last_y_Position when player click
        this.last_y_Pos = this.position.y;

        //rotation_logic
        this.rotation += 60 * rVal;
        if (rotation >= this.maxAngle) {
            rotation = this.maxAngle;
        }
        //sound-effect
        this.flap.play();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void birdFly() {
        position.y += yVal;
        if (position.y > game.HEIGHT / 2 + sprite.getTextureRegions()[0].getRegionHeight()) {
            yVal = -1;
        }
        if (position.y < game.HEIGHT / 2) {
            yVal = +1;
        }
    }

    public float getY() {
        return y;
    }

    public boolean isGroundTouchd() {
        return groundTouch;
    }

    public void setGroundTouchd(boolean input) {
        groundTouch = input;
    }

    public Animation getBird() {
        return sprite.getAnimation();
    }

}
