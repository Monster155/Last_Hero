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

import ru.itlab.lasthero.LobbyServer.ServerSide.Connector;
import ru.itlab.lasthero.MainActivity;

import static ru.itlab.lasthero.GameServer.GamePreferences.BASE_SCREEN_SIZE;

public class MenuScreen implements Screen {

    private final MainActivity ma;

    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private Stage stage;

    private Dialog dialog;
    private Vector2 btnSize;
    private boolean hasConnection;
    private BitmapFont font;

    public MenuScreen(MainActivity ma) {
        this.ma = ma;
    }

    @Override
    public void show() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                setHasConnection(Connector.here.connect());
            }
        });

        camera = new OrthographicCamera();
        camera.setToOrtho(false, BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y);
        viewport = new ExtendViewport(BASE_SCREEN_SIZE.x, BASE_SCREEN_SIZE.y, camera); // change this to your needed viewport
        stage = new Stage(viewport);

        font = new BitmapFont(Gdx.files.internal("RuEn.fnt"));

        createButtons();

        dialog = createDialog();
        stage.addActor(dialog);
        dialog.hide(Actions.visible(false));

        Gdx.input.setInputProcessor(stage);
        stage.setDebugAll(true);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
        if (!hasConnection) dialog.show(stage);
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
                if (!hasConnection) dialog.show(stage);
                else ma.setScreen(ma.lobbyListScreen);

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
}
