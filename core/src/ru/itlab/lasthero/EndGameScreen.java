package ru.itlab.lasthero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndGameScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout glyph;
    private int score;

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        glyph = new GlyphLayout(font, "Your score: 10");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        glyph.setText(font, "Your score: " + score);
        font.draw(batch, glyph, (Gdx.graphics.getWidth() - glyph.width) / 2, (Gdx.graphics.getHeight() + glyph.height) / 2);
        batch.end();
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
        batch.dispose();
        font.dispose();
    }
}
