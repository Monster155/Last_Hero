package ru.itlab.lasthero.NewLobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import ru.itlab.lasthero.MainActivity;
import ru.itlab.lasthero.Protocol;

import static ru.itlab.lasthero.GamePreferences.BASE_SCREEN_SIZE;

public class LobbyListScreen implements Screen {

    private MainActivity ma;

    private ScrollPane scrollPane;
    private VerticalGroup group;
    private TextField textField;
    private User user;
    private HashMap<Integer, Room> rooms;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Stage stage;

    public LobbyListScreen(MainActivity ma) {
        this.ma = ma;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new ExtendViewport(640, 360, camera);
        stage = new Stage(viewport);

        textField = createTextField();

        rooms = new HashMap<>();
        group = new VerticalGroup();
        group.space(10);
        group.padBottom(10);

        scrollPane = new ScrollPane(group);
        scrollPane.setBounds(0, 0, 640, 360 - textField.getHeight() - 10);

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                try {
                    user = new User(new Socket(Protocol.IP_ADDRESS, Protocol.PORT), rooms);
                } catch (IOException e) {
                    System.out.println(e.getMessage() + " : " + e.getCause());
                    //TODO show dialog window
                }
            }
        });

        stage.addActor(scrollPane);
        stage.addActor(textField);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        addNewRooms();

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

    private void addNewRooms() {
        if (rooms.size() < 1) return;
        System.out.println("Room found");
        group.addActor(new RoomActor(rooms.remove(rooms.keySet().toArray()[0]), group.getChildren().size, ma, user, textField));
    }

    private TextField createTextField() {
        TextField textField = new TextField("", new TextField.TextFieldStyle(
                new BitmapFont(Gdx.files.internal("RuEn.fnt")),
                Color.WHITE,
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Back.png")))),      //cursor
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Cursor.png")))),    //selector
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Selector.png"))))));//background
        textField.setBounds(BASE_SCREEN_SIZE.x / 2 - 150, BASE_SCREEN_SIZE.y - 60, 300, 60);
        return textField;
    }
}
