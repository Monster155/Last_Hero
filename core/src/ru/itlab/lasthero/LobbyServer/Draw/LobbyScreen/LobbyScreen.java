package ru.itlab.lasthero.LobbyServer.Draw.LobbyScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import ru.itlab.lasthero.ServerSide.Room;
import ru.itlab.lasthero.ServerSide.User;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.BASE_SCREEN_SIZE;

public class LobbyScreen implements Screen {

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Stage stage;

    private ScrollPane scrollPane;
    private VerticalGroup group;
    private LobbyInfoActor lobbyInfoActor;

    private ArrayList<User> users;
    private Dialog dialog;
    private float timeBeforeStart;
    private boolean showTimer;
    private Room room;

    public LobbyScreen() {
        users = new ArrayList<>();
        showTimer = false;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        lobbyInfoActor = new LobbyInfoActor(room);

        group = new VerticalGroup();
        group.space(10);
        group.padBottom(10);

        scrollPane = new ScrollPane(group);
        scrollPane.setBounds(0, 0, 640, 360 - lobbyInfoActor.getHeight());

        stage.addActor(lobbyInfoActor);
        stage.addActor(scrollPane);

        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true);
        //TODO add button to leave lobby - connector.here.disconnect()
        // change InfoParent to ServerInfo
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkNewUsers();
        if (showTimer) {
            timeBeforeStart -= delta;
            // TODO change glyph text
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().setScreenSize(width, height);
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
        stage.dispose();
    }

    public void addNewUser(User user) {
        users.add(user);
        room.addUser(user.getUserId(), user);
    }

    private void checkNewUsers() {
        if (users.size() < 1) return;
        UserActor ua = new UserActor(users.size(), users.get(0));
        users.remove(0).addActor(ua);
        group.addActor(ua);
    }

    public void setTimer(int time) {
        timeBeforeStart = time;
        showTimer = true;
    }

    public void stopTimer() {
        showTimer = false;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

}
