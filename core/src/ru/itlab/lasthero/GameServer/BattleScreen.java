package ru.itlab.lasthero.GameServer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;

import ru.itlab.lasthero.GameServer.Player.OtherUser;
import ru.itlab.lasthero.GameServer.Player.Player;

import static ru.itlab.lasthero.GameServer.GamePreferences.BASE_SCREEN_SIZE;

public class BattleScreen implements Screen {

    Texture testObjectTexture;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Player player;
    private Controller controller;
    private Connection connection;
    private OtherUsersController otherUsersController;

    public BattleScreen(Controller.ModuleID moduleID) {
        controller = new Controller(moduleID);
    }

    @Override
    public void show() {
        //TODO remove this
        testObjectTexture = new Texture(Gdx.files.internal("top-down-shooter-1/background/door.gif"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        //TODO set loading screen before server sends coordinates
        player = new Player(camera);
        stage.addActor(player);

        stage.addActor(controller);
        Gdx.input.setInputProcessor(stage);

//        otherUsers = new HashMap<>();
//        otherUsersToUpdate = new ArrayList<>();
//        connection = new Connection(player, otherUsersToUpdate);
//        connection.start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateOtherPlayers();

        stage.act();
        stage.draw();
        stage.getBatch().begin();
        stage.getBatch().draw(testObjectTexture, 0, 0, 20, 20);
        stage.getBatch().end();

        connection.send();
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

    private void updateOtherPlayers() {
//        if (otherUsersToUpdate.size() < 1) return;
//        boolean isNew = otherUsersToUpdate.get(0).getKey();
//        OtherUser user = otherUsersToUpdate.remove(0).getValue();
//        if (isNew) {
//            otherUsers.put(user.getIdInConnectedRoom(), user);
//        } else {
//            otherUsers.remove(user.getIdInConnectedRoom()).disconnect();
//        }
    }
}
