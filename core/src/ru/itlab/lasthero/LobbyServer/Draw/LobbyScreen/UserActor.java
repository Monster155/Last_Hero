package ru.itlab.lasthero.LobbyServer.Draw.LobbyScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;

import ru.itlab.lasthero.GameServer.Utils.GamePreferences;
import ru.itlab.lasthero.ServerSide.User;

public class UserActor extends Actor {

    private User user;
    private BitmapFont font;
    private GlyphLayout glyphName, glyphIP;

    public UserActor(int count, User user) {
        this.user = user;
        font = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        glyphName = new GlyphLayout(font, user.getName(), Color.BLUE, 100, Align.left, false);
        glyphIP = new GlyphLayout(font, user.getIp());
        setBounds(0,
                GamePreferences.BASE_SCREEN_SIZE.y / 5 * count,
                GamePreferences.BASE_SCREEN_SIZE.x,
                GamePreferences.BASE_SCREEN_SIZE.y / 5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, glyphName, 20, (getHeight() + glyphName.height) / 2 + getY());
        font.draw(batch, glyphIP, getWidth() - glyphIP.width - 20, (getHeight() + glyphName.height) / 2 + getY());
    }

    @Override
    public void act(float delta) {
    }

    public void delete() {
        getParent().removeActor(this);
    }
}
