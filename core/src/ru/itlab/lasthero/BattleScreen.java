package ru.itlab.lasthero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.itlab.lasthero.Player.Player;

import static ru.itlab.lasthero.GamePreferences.BASE_SCREEN_SIZE;

public class BattleScreen implements Screen {

    Texture testObjectTexture;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Player player;
    private Controller controller;

    public BattleScreen(Controller.ModuleID moduleID) {
        controller = new Controller(moduleID);
    }

    @Override
    public void show() {
        testObjectTexture = new Texture(Gdx.files.internal("top-down-shooter-1/background/door.gif"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        player = new Player(new Vector2(20, 20), camera);
        stage.addActor(player);

        stage.addActor(controller);
        Gdx.input.setInputProcessor(stage);
//        camera.position.set(0, 0, camera.position.z);
//        camera.update();
        Connection connection = new Connection(player);
        connection.start();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
        stage.getBatch().begin();
        stage.getBatch().draw(testObjectTexture, 0, 0, 20, 20);
        stage.getBatch().end();
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
}
