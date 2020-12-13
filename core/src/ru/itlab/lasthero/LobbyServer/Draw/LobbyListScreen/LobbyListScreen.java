package ru.itlab.lasthero.LobbyServer.Draw.LobbyListScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import ru.itlab.lasthero.LobbyServer.ServerSide.Room;

import static ru.itlab.lasthero.GameServer.GamePreferences.BASE_SCREEN_SIZE;

public class LobbyListScreen implements Screen {

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Stage stage;

    private ScrollPane scrollPane;
    private VerticalGroup group;
    private TextField textField;
    private Dialog dialog;
    private boolean showDialog;

    private ArrayList<Room> rooms;

    public LobbyListScreen() {
        rooms = new ArrayList<>();
        showDialog = false;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        textField = createTextField();

        group = new VerticalGroup();
        group.space(10);
        group.padBottom(10);

        scrollPane = new ScrollPane(group);
        scrollPane.setBounds(0, 0, 640, 360 - textField.getHeight() - 20);

        stage.addActor(scrollPane);
        stage.addActor(textField);

        dialog = createDialog();
        stage.addActor(dialog);
        dialog.hide(Actions.visible(false));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkNewRooms();
        if (showDialog) {
            dialog.show(stage);
            showDialog = false;
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

    public void addNewRoom(Room room) {
        rooms.add(room);
    }

    private void checkNewRooms() {
        if (rooms.size() < 1) return;
        RoomActor ra = new RoomActor(rooms.size(), rooms.get(0), textField);
        rooms.remove(0).addActor(ra);
        group.addActor(ra);
    }

    private TextField createTextField() {
        TextField textField = new TextField("", new TextField.TextFieldStyle(
                new BitmapFont(Gdx.files.internal("RuEn.fnt")),
                Color.WHITE,
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Back.png")))),      //cursor
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Cursor.png")))),    //selector
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Selector.png"))))));//background
        textField.setBounds(BASE_SCREEN_SIZE.x / 2 - 150, BASE_SCREEN_SIZE.y - 70, 300, 60);
        return textField;
    }

    private Dialog createDialog() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        Dialog dialog = new Dialog("", new Window.WindowStyle(font, Color.BLACK,
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("UI/TF_Selector.png"))))));
        dialog.setBounds(BASE_SCREEN_SIZE.x / 2 - 200, BASE_SCREEN_SIZE.y / 2 - 100, 400, 200);
        Sprite sprite = new Sprite(new Texture(Gdx.files.internal("UI/TF_Cursor.png")));
        sprite.setSize(200, 60);
        Button btn = new Button(new SpriteDrawable(sprite));

        Label btnLabel = new Label("Ok", new Label.LabelStyle(font, Color.WHITE));
        btnLabel.setHeight(60);
        btnLabel.setX(100 - btnLabel.getWidth() / 2);
        btn.addActor(btnLabel);

        Label infoLabel = new Label("Can't connect\nto server", new Label.LabelStyle(font, Color.WHITE));
        infoLabel.setAlignment(Align.center);
        infoLabel.setSize(300, 100);
        infoLabel.setX(200 - infoLabel.getWidth() / 2);

        dialog.button(btn);
        dialog.text(infoLabel);
        dialog.getButtonTable().padBottom(10);
        return dialog;
    }

    public void setShowDialog() {
        this.showDialog = true;
    }
}
