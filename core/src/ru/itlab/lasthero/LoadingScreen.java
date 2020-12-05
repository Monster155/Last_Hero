package ru.itlab.lasthero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.itlab.lasthero.Lobby.LobbyScreen;

import static ru.itlab.lasthero.GamePreferences.BASE_SCREEN_SIZE;

public class LoadingScreen implements Screen {

    private MainActivity ma;
    private LobbyScreen lobbyScreen;
    private Texture texture;
    private SpriteBatch batch;
    private float size;

    public LoadingScreen(MainActivity ma, LobbyScreen lobbyScreen) {
        this.ma = ma;
        this.lobbyScreen = lobbyScreen;
    }

    @Override
    public void show() {
        texture = new Texture(Gdx.files.internal("UI/SandWatch.png"));
        batch = new SpriteBatch();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                // Do something on the main thread
                lobbyScreen.show();
            }
        });
        size = BASE_SCREEN_SIZE.x / 20;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(texture, (BASE_SCREEN_SIZE.x - size) / 2, (BASE_SCREEN_SIZE.y - size) / 2, size, size);
        batch.end();

//        if (lobbyScreen.isReady()) {
//            ma.setScreen(lobbyScreen);
//        }
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
        texture.dispose();
        batch.dispose();
    }
}
