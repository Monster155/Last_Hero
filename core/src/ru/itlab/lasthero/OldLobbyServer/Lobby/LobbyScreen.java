package ru.itlab.lasthero.OldLobbyServer.Lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.itlab.lasthero.MainActivity;
import ru.itlab.lasthero.OldLobbyServer.NewLobby.Room;
import ru.itlab.lasthero.OldLobbyServer.NewLobby.User;

import static ru.itlab.lasthero.GameServer.GamePreferences.BASE_SCREEN_SIZE;

public class LobbyScreen implements Screen {

    private MainActivity ma;

    private User user;
    private Room room;
    private ru.itlab.lasthero.OldLobbyServer.Lobby.LobbyInfoActor lobbyInfoActor;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;

    private Group lobbyUsers;

    public LobbyScreen(MainActivity ma) {
        this.ma = ma;
    }

    public void init(User user, Room room) {
        this.user = user;
        this.room = room;
        user.connectToRoom(room);
    }

    @Override
    public void show() {
        lobbyUsers = new Group();
        lobbyInfoActor = new LobbyInfoActor(room);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        stage.addActor(lobbyInfoActor);
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
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void addNewPlayer() {
        if (room.getUsers().size() < 1) return;
        System.out.println("New users count: " + room.getUsers().size());
        lobbyUsers.addActor(
                new LobbyUserActor(
                        room.getUsers().remove(room.getUsers().keySet().toArray()[0]),
                        lobbyUsers.getChildren().size));
    }
}
