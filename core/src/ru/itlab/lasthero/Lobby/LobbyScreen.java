package ru.itlab.lasthero.Lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import static ru.itlab.lasthero.GamePreferences.BASIC_SCREEN_SIZE;

public class LobbyScreen implements Screen {

    private Group lobbyUsers;
    private ArrayList<ConnectedUser> users;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    @Override
    public void show() {
        lobbyUsers = new Group();
        users = new ArrayList<>();
        NewUsersConnector connector = new NewUsersConnector(users);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASIC_SCREEN_SIZE.x, BASIC_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASIC_SCREEN_SIZE.x, BASIC_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        stage.addActor(lobbyUsers);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        addNewPlayer();

        stage.act();
        stage.draw();
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

    public void addNewPlayer() {
        if (users.size() < 1) return;
        System.out.println("New users count: " + users.size());
        for (ConnectedUser user : users) {
            lobbyUsers.addActor(new LobbyUser(user, lobbyUsers.getChildren().size));
        }
        users.clear();
    }
}
