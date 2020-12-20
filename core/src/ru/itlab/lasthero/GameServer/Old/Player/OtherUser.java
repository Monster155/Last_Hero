package ru.itlab.lasthero.GameServer.Old.Player;

import com.badlogic.gdx.math.Vector2;

public class OtherUser {
    private String name;
    private int idInConnectedRoom;
    private OtherPlayer actor;

    public OtherUser(String name, String idInConnectedRoom) {
        this.name = name;
        this.idInConnectedRoom = Integer.parseInt(idInConnectedRoom);
        actor = new OtherPlayer(new Vector2(0, 0));
    }

    public String getName() {
        return name;
    }

    public int getIdInConnectedRoom() {
        return idInConnectedRoom;
    }

    public void disconnect() {
        actor.getParent().removeActor(actor);
    }
}
