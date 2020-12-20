package ru.itlab.lasthero.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import ru.itlab.lasthero.ServerSide.ConnectionThread;
import ru.itlab.lasthero.MainActivity;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.BASE_SCREEN_SIZE;

public class MenuScreen implements Screen {

    private final MainActivity ma;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Stage stage;

    private Dialog dialog;
    private Vector2 btnSize;
    private BitmapFont font;

    private boolean canDrawLoading;
    private Sprite loading;
    private float loadingDegree;

    private ConnectionThread connectionThread;
    private Label labelDisable, labelEnable;

    public MenuScreen(MainActivity ma) {
        this.ma = ma;
    }

    @Override
    public void show() {
        connectionThread = new ConnectionThread();
        canDrawLoading = false;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        font = new BitmapFont(Gdx.files.internal("RuEn.fnt"));

        createButtons();
        loadingDegree = 0;
        loading = new Sprite(new Texture(Gdx.files.internal("UI/loading.png")));
        loading.setBounds(0, (360 - 640) / 2f, 640, 640);

        dialog = createDialog();
        stage.addActor(dialog);
        dialog.hide(Actions.alpha(0));

        createServerConnectionText();

        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        setServerConnection(!connectionThread.isConnected());

//        if (canDrawLoading) {
//            System.out.println("draw");
//            loadingDegree += delta * 100;
//            loading.setRotation(loadingDegree);
//
//            stage.getBatch().begin();
//            loading.draw(stage.getBatch());
//            stage.getBatch().end();
//        }
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

    private NinePatch getScaledNinePatch(Texture texture, int left, int right, int top, int bottom) {
        NinePatch ninePatch = new NinePatch(texture, left, right, top, bottom);
        float scaleX = btnSize.x / texture.getWidth();
        float scaleY = btnSize.y / texture.getHeight();
        float scale = scaleX > scaleY ? scaleY : scaleX;
        ninePatch.scale(scale, scale);
        return ninePatch;
    }

    private Dialog createDialog() {
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

    private void createButtons() {
        btnSize = new Vector2(300, 100);
        // creating "Start" button
        Button startBtn = new Button(new NinePatchDrawable(getScaledNinePatch(
                new Texture(Gdx.files.internal("UI/BtnImg.png")), 2, 2, 2, 2)));
        startBtn.setBounds((BASE_SCREEN_SIZE.x - btnSize.x) / 2, BASE_SCREEN_SIZE.y / 2, btnSize.x, btnSize.y);
        startBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!connectionThread.isConnected()) {
                    dialog.show(stage);
                } else {
                    connectionThread.interrupt();
                    ma.setScreen(ma.lobbyListScreen);
                }

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        createLabel(startBtn, "Start");

        // creating "Settings" button
        Button settingsBtn = new Button(new NinePatchDrawable(getScaledNinePatch(
                new Texture(Gdx.files.internal("UI/BtnImg.png")), 2, 2, 2, 3)));
        settingsBtn.setBounds((BASE_SCREEN_SIZE.x - btnSize.x) / 2, BASE_SCREEN_SIZE.y / 2 - btnSize.y - 10, btnSize.x, btnSize.y);
        settingsBtn.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        createLabel(settingsBtn, "Settings");

        stage.addActor(startBtn);
        stage.addActor(settingsBtn);
    }

    private void createLabel(Button parent, String text) {
        Label label = new Label(text, new Label.LabelStyle(font, Color.WHITE));
        parent.addActor(label);
        label.setAlignment(Align.center);
        label.setFillParent(true);
    }

    private void createServerConnectionText() {
        labelDisable = new Label("Server disable", new Label.LabelStyle(font, Color.RED));
        labelDisable.setPosition((BASE_SCREEN_SIZE.x - labelDisable.getWidth()) / 2, BASE_SCREEN_SIZE.y - 60);
        stage.addActor(labelDisable);
        labelDisable.setVisible(true);
        labelEnable = new Label("Server enable", new Label.LabelStyle(font, Color.GREEN));
        labelEnable.setPosition((BASE_SCREEN_SIZE.x - labelEnable.getWidth()) / 2, BASE_SCREEN_SIZE.y - 60);
        stage.addActor(labelEnable);
        labelEnable.setVisible(false);
    }

    private void setServerConnection(boolean isDisabled) {
        labelDisable.setVisible(isDisabled);
        labelEnable.setVisible(!isDisabled);
    }
}
