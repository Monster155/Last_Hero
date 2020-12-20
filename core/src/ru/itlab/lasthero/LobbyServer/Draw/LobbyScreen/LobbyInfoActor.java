package ru.itlab.lasthero.LobbyServer.Draw.LobbyScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.ServerSide.Room;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.BASE_SCREEN_SIZE;

public class LobbyInfoActor extends Actor {

    private Room room;
    private Texture back;
    private BitmapFont fontMain, fontSmall;
    private GlyphLayout glyphName, glyphPlayersCount, glyphIP;

    public LobbyInfoActor(Room room) {
        this.room = room;
        setBounds(0, BASE_SCREEN_SIZE.y - 100, BASE_SCREEN_SIZE.x, 100);
        back = new Texture(Gdx.files.internal("UI/TF_Selector.png"));
        fontMain = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        glyphName = new GlyphLayout(fontMain, room.getName());
        glyphPlayersCount = new GlyphLayout(fontMain, room.getPlayersCount() + "/" + room.getMaxPlayersCount());
        fontSmall = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        fontSmall.getData().setScale(0.6f);
        glyphIP = new GlyphLayout(fontSmall, room.getIp());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(back, getX(), getY(), getWidth(), getHeight());
        fontMain.draw(batch, glyphName, 10 + getX(), (getHeight() + glyphName.height) / 2 + 15 + getY());
        fontMain.draw(batch, glyphPlayersCount, getWidth() - glyphPlayersCount.width - 10 + getX(), (getHeight() + glyphName.height) / 2 + getY());
        fontSmall.draw(batch, glyphIP, 10 + getX(), (getHeight() + glyphName.height) / 2 - 15 + getY());
    }

    @Override
    public void act(float delta) {
        glyphName.setText(fontMain, room.getName());
        glyphPlayersCount.setText(fontMain, room.getPlayersCount() + "/" + room.getMaxPlayersCount());
        glyphIP.setText(fontSmall, room.getIp());
    }
}
