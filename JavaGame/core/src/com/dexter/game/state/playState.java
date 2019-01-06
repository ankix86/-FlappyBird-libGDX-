package com.dexter.game.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dexter.game.spritesSheet.*;
import com.dexter.game.entity.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dexter.game.gui.menu;
import com.dexter.game.game;

public class playState extends state {

    bird bird;
    pipe pipes;
    private Sound die, hit, point;
    sprites background;
    boolean isTouch = false, collide = false;
    menu menu;
    game game;

    public playState(GameStateManager gsm) {
        super(gsm);
        game = new game();
        menu = new menu();
        isTouch = false;
        this.background = new sprites("background-night");
        this.bird = new bird();
        this.pipes = new pipe();
        this.die = Gdx.audio.newSound(Gdx.files.internal("audio\\die.ogg"));
        this.hit = Gdx.audio.newSound(Gdx.files.internal("audio\\hit.ogg"));
        this.point = Gdx.audio.newSound(Gdx.files.internal("audio\\point.ogg"));
        cam.setToOrtho(false, game.WIDTH, game.HEIGHT);
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched() && !collide) {
            if (!isTouch && !game.isGameOver()) {
                isTouch = true;
            }
            bird.lift();
        }
        if (Gdx.input.isTouched()) {
            if (Gdx.input.getX() > menu.getPlayButtonPos().x && Gdx.input.getX() < menu.getPlayButtonPos().x + menu.getPlayButton().getWidth()) {
                if (Gdx.input.getY() > 370 && Gdx.input.getY() < 426 && menu.getGameOverPos()) {
                    if (game.isGameOver()) {
                        game.setGameOver(false);
                        bird.setGroundTouchd(false);
                        collide = false;
                        menu.setGameOverPos(false);
                        gsm.set(new playState(gsm));
                    }
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        if (!isTouch) {
            bird.birdFly();
        } else {
            bird.update(dt);
            if (!collide) {
                pipes.movement(dt);
            }
        }
        handleInput();
        //Pipe Movement
        if (!collide) {
            pipes.pipePositioning();
            pipes.groundMovement();
        }
        if (game.gameOver && collide) {
            menu.update();
        }
        if (!game.gameOver) {
            collision();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        game.endTime += Gdx.graphics.getDeltaTime();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(this.background.getSprite(), 0, 0, game.WIDTH, game.HEIGHT);
        for (int i = 0; i < pipes.topPipePoss.length; i++) {
            sb.draw(pipes.getButtomPipeTex(), pipes.getButtomPipePos()[i].x, pipes.getButtomPipePos()[i].y, pipes.getButtomPipeTex().getWidth(), pipes.getButtomPipeTex().getHeight());
            sb.draw(pipes.getTopPipeTex(), pipes.getTopPipePos()[i].x, pipes.getTopPipePos()[i].y, pipes.getTopPipeTex().getWidth(), pipes.getTopPipeTex().getHeight());
        }
        //birdAnimation-Rendering
        sb.draw((TextureRegion) bird.getBird().getKeyFrame(game.endTime, true),
                bird.getPosition().x, bird.getPosition().y,
                bird.sprite.getTextureRegions()[0].getRegionWidth() / 2,
                bird.sprite.getTextureRegions()[0].getRegionHeight() / 2,
                bird.sprite.getTextureRegions()[0].getRegionWidth(),
                bird.sprite.getTextureRegions()[0].getRegionHeight(),
                1, 1, bird.rotation);

        //Game-Over State
        sb.draw(menu.getGameOver(), game.WIDTH / 2 - menu.getGameOver().getWidth() / 2, menu.getY() + menu.getProgress().getHeight());
        sb.draw(menu.getProgress(), game.WIDTH / 2 - menu.getProgress().getWidth() / 2, menu.getY());
        menu.getFont().getData().setScale(1);
        menu.getFont().draw(sb, String.valueOf(game.getScore()), game.WIDTH - 100, menu.getY() + 90);

        //BEST---SCORE//
        menu.getFont().draw(sb, String.valueOf(game.getScore()), game.WIDTH - 100, menu.getY() + 50);

        //--------------------------COIN-------------------------\\
        if (game.getScore() > 10) {
            sb.draw(menu.getScoin(), 80, menu.getY() + menu.getScoin().getHeight() - 10);
        }
        if (game.getScore() > 20) {
            sb.draw(menu.getGcoin(), 80, menu.getY() + menu.getGcoin().getHeight() - 10);
        }
        //--------------------------COIN-------------------------\\


        if (menu.getGameOverPos()) {
            sb.draw(menu.getPlayButton(), menu.getPlayButtonPos().x, menu.getPlayButtonPos().y);
            sb.draw(menu.getPositionButton(), game.WIDTH - menu.getPositionButton().getWidth() - 30, menu.getPlayButtonPos().y);
        }

        for (int i = 0; i < pipes.groundPos.length; i++) {
            sb.draw(pipes.getGroundTex(), pipes.getGroundPos()[i].x, pipes.getGroundPos()[i].y, pipes.getGroundTex().getWidth(), pipes.getGroundTex().getHeight());
        }
        if (!game.isGameOver()) {
            menu.getFont().getData().setScale(2);
            menu.getFont().draw(sb, String.valueOf(game.getScore()), game.WIDTH / 2, game.HEIGHT - 100);
        }
//        if(game.gameOver) {
//            sb.draw(menu.getTouchToStart(), game.WIDTH / 2 - menu.getTouchToStart().getWidth() / 2, game.HEIGHT / 2 - menu.getTouchToStart().getHeight() / 2);
//        }
        //Score
        sb.end();
    }

    public void collision() {
        for (int i = 0; i <= 2; i++) {
            if (bird.getPosition().x + bird.sprite.getTextureRegions()[0].getRegionWidth() - 5 >= pipes.getTopPipePos()[i].x &&
                    bird.getPosition().x <= pipes.getTopPipePos()[i].x + pipes.getTopPipeTex().getWidth()) {
                {
                    if (bird.getPosition().y + bird.sprite.getTextureRegions()[0].getRegionHeight() >= pipes.getTopPipePos()[i].y || bird.getPosition().y <= pipes.getButtomPipePos()[i].y + pipes.getTopPipeTex().getHeight()) {
                        if (!collide) {
                            hit.play();
                            die.play();
                            collide = true;
                        }
                        if (bird.isGroundTouchd() && collide) {
                            game.gameOver = true;
                        }
                    }
                }
            }
            if (bird.getPosition().x >= pipes.getTopPipePos()[i].x &&
                    bird.getPosition().x <= pipes.getTopPipePos()[i].x + pipes.getTopPipeTex().getWidth() - 49.5) {
                if (!game.gameOver) {
                    game.setScore(game.getScore() + 1);
                    point.play();
                }
            }
        }
    }
}
