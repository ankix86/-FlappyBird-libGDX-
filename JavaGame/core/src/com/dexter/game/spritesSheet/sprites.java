package com.dexter.game.spritesSheet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class sprites {
    public Animation animation;
    public TextureRegion[] textureRegions;
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spritesSheet\\atlas.atlas"));
    private Sprite sprite;

    public sprites(int regionSize, float frameDuration, String nameOfanimation, boolean index) {
        this.textureRegions = new TextureRegion[regionSize];
        if (index) {
            for (int i = 0; i < this.textureRegions.length; i++) {
                this.textureRegions[i] = new TextureRegion(this.textureAtlas.findRegion(nameOfanimation, i));
            }
        } else {
            for (int i = 0; i < this.textureRegions.length; i++) {
                this.textureRegions[i] = new TextureRegion(this.textureAtlas.findRegion(nameOfanimation + i));
            }
        }

        animation = new Animation(frameDuration, this.textureRegions);
    }

    public sprites(String nameOfTexture) {
        this.sprite = new Sprite(this.textureAtlas.findRegion(nameOfTexture));
    }

    public sprites(String nameOfTexture, int indexNum) {
        this.sprite = new Sprite(this.textureAtlas.findRegion(nameOfTexture, indexNum));
    }

    public Animation getAnimation() {
        return animation;
    }

    public TextureRegion[] getTextureRegions() {
        return textureRegions;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public Sprite getSprite() {
        return sprite;
    }
}

