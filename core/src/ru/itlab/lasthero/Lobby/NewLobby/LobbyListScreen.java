package ru.itlab.lasthero.Lobby.NewLobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.ArrayList;

public class LobbyListScreen implements Screen {

    private ScrollPane scrollPane;
    private VerticalGroup group;
    private SocketUser socketUser;
    private ArrayList<LobbyInfo> lobbyInfos;

    @Override
    public void show() {
        lobbyInfos = new ArrayList<>();
        group = new VerticalGroup();
        scrollPane = new ScrollPane(group);
        socketUser = new SocketUser(lobbyInfos);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
