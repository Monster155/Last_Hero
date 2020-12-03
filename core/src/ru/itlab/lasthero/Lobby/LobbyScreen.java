package ru.itlab.lasthero.Lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import ru.itlab.lasthero.MainActivity;

import static ru.itlab.lasthero.GamePreferences.BASE_SCREEN_SIZE;

public class LobbyScreen implements Screen {

    private Group lobbyUsers;
    private ArrayList<ConnectedUser> users;

    private MainActivity mainActivity;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private boolean isReady = false;

    public LobbyScreen(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void init() {
        users = new ArrayList<>();
        NewUsersConnector connector = new NewUsersConnector(users);
        if (!connector.isHasConnection()) {
            mainActivity.menuScreen.setHasNoConnection(true);
            mainActivity.setScreen(mainActivity.menuScreen);
        }
        isReady = true;
    }

    @Override
    public void show() {
        lobbyUsers = new Group();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        stage.addActor(lobbyUsers);

        TextField textField = new TextField("", new TextField.TextFieldStyle(
                new BitmapFont(Gdx.files.internal("RuEn.fnt")),
                Color.WHITE,
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Cursor.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Selector.png")))),
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Back.png"))))));
        textField.setBounds(0, BASE_SCREEN_SIZE.y - 300, 100, 300);
        stage.addActor(textField);

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
        if (users.size() < 1) return;
        System.out.println("New users count: " + users.size());
        for (ConnectedUser user : users) {
            lobbyUsers.addActor(new LobbyUser(user, lobbyUsers.getChildren().size));
        }
        users.clear();
    }

    public boolean isReady() {
        return isReady;
    }
}
