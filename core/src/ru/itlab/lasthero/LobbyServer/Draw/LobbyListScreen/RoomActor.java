package ru.itlab.lasthero.LobbyServer.Draw.LobbyListScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ru.itlab.lasthero.LobbyServer.ServerSide.Connector;
import ru.itlab.lasthero.LobbyServer.ServerSide.Room;

public class RoomActor extends Actor {

    private Room room;
    private Texture back;
    private BitmapFont fontMain, fontSmall;
    private GlyphLayout glyphName, glyphPlayersCount, glyphIP;

    public RoomActor(int count, final Room room, final TextField textField) {
        this.room = room;
        setBounds(20, 110 * count, 600, 100);
        back = new Texture(Gdx.files.internal("UI/TF_Selector.png"));
        fontMain = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        glyphName = new GlyphLayout(fontMain, room.getName());
        glyphPlayersCount = new GlyphLayout(fontMain, room.getPlayersCount() + "/" + room.getMaxPlayersCount());
        fontSmall = new BitmapFont(Gdx.files.internal("RuEn.fnt"));
        fontSmall.getData().setScale(0.6f);
        glyphIP = new GlyphLayout(fontSmall, room.getIp());

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String name = textField.getText();
                System.out.println(name);
                if(name.equals(""))
                    name = "Player";
                Connector.here.connectToRoom(room.getId(), name);
                return true;
            }
        });
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

    public void delete() {
        getParent().removeActor(this);
    }
}
