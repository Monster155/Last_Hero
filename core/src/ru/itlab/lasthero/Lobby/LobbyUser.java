package ru.itlab.lasthero.Lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.GamePreferences;

public class LobbyUser extends Actor {

    public LobbyUser() {
        setBounds(0, 0,
                GamePreferences.BASIC_SCREEN_SIZE.x * 2 / 3,
                GamePreferences.BASIC_SCREEN_SIZE.y / 5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


    }

    @Override
    public void act(float delta) {

    }
}
