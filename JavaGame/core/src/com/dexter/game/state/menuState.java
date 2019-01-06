package com.dexter.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dexter.game.spritesSheet.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dexter.game.game;
import com.dexter.game.gui.menu;
import com.dexter.game.entity.*;

public class menuState extends state {
    private sprites background, flappyBird, rateButton;
    private bird bird;
    private menu menu;
    private pipe ground;

    public menuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);
        menu = new menu();
        ground = new pipe();
        bird = new bird();
        rateButton = new sprites("rate");
        flappyBird = new sprites("title");
        background = new sprites("background-night");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > menu.getPlayButtonPos().x && Gdx.input.getX() < menu.getPlayButtonPos().x + menu.getPlayButton().getWidth()) {
                if (Gdx.input.getY() > 370 && Gdx.input.getY() < 426) {
                    gsm.set(new playState(gsm));
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        ground.groundMovement();
        bird.birdFly();
    }

    @Override
    public void render(SpriteBatch sb) {
        game.endTime += Gdx.graphics.getDeltaTime();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background.getSprite(), 0, 0, game.WIDTH, game.HEIGHT);
        sb.draw(flappyBird.getSprite(), game.WIDTH / 2 - flappyBird.getSprite().getWidth() / 2, game.HEIGHT / 2 + flappyBird.getSprite().getHeight() * 2);
        sb.draw(rateButton.getSprite(), game.WIDTH / 2 - rateButton.getSprite().getWidth() / 2, game.HEIGHT / 3 + 25);
        sb.draw(menu.getPositionButton(), game.WIDTH - menu.getPositionButton().getWidth() - 30, game.HEIGHT / 4);
        sb.draw(menu.getPlayButton(), menu.getPlayButtonPos().x, game.HEIGHT / 4);

        for (int i = 0; i < ground.groundPos.length; i++) {
            sb.draw(ground.getGroundTex(), ground.getGroundPos()[i].x, ground.getGroundPos()[i].y, ground.getGroundTex().getWidth(), ground.getGroundTex().getHeight());
        }

        sb.draw((TextureRegion) bird.getBird().getKeyFrame(game.endTime, true),
                game.WIDTH / 2 - bird.sprite.getTextureRegions()[0].getRegionWidth() / 2, bird.getPosition().y,
                bird.sprite.getTextureRegions()[0].getRegionWidth() / 2,
                bird.sprite.getTextureRegions()[0].getRegionHeight() / 2,
                bird.sprite.getTextureRegions()[0].getRegionWidth(),
                bird.sprite.getTextureRegions()[0].getRegionHeight(),
                1, 1, bird.rotation);
        sb.end();
    }
}
