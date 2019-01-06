package com.dexter.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dexter.game.game;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = game.WIDTH;
        config.height = game.HEIGHT;
        config.title = game.TITLE;
        new LwjglApplication(new game(), config);
    }
}
