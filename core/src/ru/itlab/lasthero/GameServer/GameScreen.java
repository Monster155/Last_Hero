package ru.itlab.lasthero.GameServer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;
import java.util.HashMap;

import ru.itlab.lasthero.GameServer.GameObjects.Item;
import ru.itlab.lasthero.GameServer.GameObjects.ItemActor;
import ru.itlab.lasthero.GameServer.GameObjects.Level;
import ru.itlab.lasthero.GameServer.Players.Enemy;
import ru.itlab.lasthero.GameServer.Players.EnemyActor;
import ru.itlab.lasthero.GameServer.Players.Player;
import ru.itlab.lasthero.GameServer.Players.PlayerActor;
import ru.itlab.lasthero.GameServer.Utils.Controller;
import ru.itlab.lasthero.GameServer.Utils.Controller.ModuleID;
import ru.itlab.lasthero.ServerSide.Connector;
import ru.itlab.lasthero.ServerSide.GameDataSender;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.BASE_SCREEN_SIZE;

public class GameScreen implements Screen {

    private final float STEP_TIME = 1f / 60f;
    private float accumulator = 0;
    private World world;
    private Box2DDebugRenderer b2ddr;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Stage stage;

    private Level level;
    private PlayerActor playerActor;
    private Player player;
    private ArrayList<Enemy> updEnemies;
    private ArrayList<Item> updItems;
    private Controller controller;

    private GameDataSender gds;

    public GameScreen(ModuleID moduleID) {
        controller = new Controller(moduleID);
        updEnemies = new ArrayList<>();
        updItems = new ArrayList<>();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        world = new World(new Vector2(0, 0), true);
        b2ddr = new Box2DDebugRenderer();

        level = new Level(stage.getBatch(), world);
        stage.addActor(level);

        playerActor = new PlayerActor(player, world, camera);
        stage.addActor(playerActor);

        stage.addActor(controller);

        updateItems();
        updateEnemies();

        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true);

        Connector.here.ready();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        step(delta);
        b2ddr.render(world, stage.getCamera().combined);

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

    private void step(float delta) {
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, 6, 2);
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void addItems(HashMap<Integer, Item> items) {
        updItems.addAll(items.values());
    }

    private void updateItems() {
        while (updItems.size() > 0) {
            Item item = updItems.remove(0);
            ItemActor actor = new ItemActor(item);
            item.setActor(actor);
            stage.addActor(actor);
        }
    }

    public void addEnemies(HashMap<Integer, Enemy> enemies) {
        updEnemies.addAll(enemies.values());
    }

    private void updateEnemies() {
        while (updEnemies.size() > 0) {
            Enemy enemy = updEnemies.remove(0);
            EnemyActor actor = new EnemyActor(enemy);
            enemy.setActor(actor);
            stage.addActor(actor);
        }
    }

    public void startGame() {
        gds = new GameDataSender(playerActor);
        controller.setEnableInput(true);
    }
}
