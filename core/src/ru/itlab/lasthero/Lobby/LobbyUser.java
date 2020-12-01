package ru.itlab.lasthero.Lobby;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.GamePreferences;

public class LobbyUser extends Actor {

    private ConnectedUser user;
    private BitmapFont font;
    private GlyphLayout glyphName, glyphIP;

    public LobbyUser(ConnectedUser user, int id) {
        this.user = user;
        font = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        glyphName = new GlyphLayout(font, user.getName());
        glyphIP = new GlyphLayout(font, user.getIpAddress());
        setBounds(0,
                GamePreferences.BASIC_SCREEN_SIZE.y / 5 * id,
                GamePreferences.BASIC_SCREEN_SIZE.x,
                GamePreferences.BASIC_SCREEN_SIZE.y / 5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        Gdx.gl.glClearColor(0, 0, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        font.draw(batch, glyphName, 20, (getHeight() + glyphName.height) / 2 + getY());
        font.draw(batch, glyphIP, getWidth() - glyphIP.width - 20, (getHeight() + glyphName.height) / 2 + getY());
        System.out.println(user.getName() + " y = " + ((getHeight() + glyphName.height) / 2 + getY()));
    }

    @Override
    public void act(float delta) {
    }
}
