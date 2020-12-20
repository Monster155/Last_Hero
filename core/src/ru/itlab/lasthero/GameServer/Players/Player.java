package ru.itlab.lasthero.GameServer.Players;

import com.badlogic.gdx.math.Vector2;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.MAP_SCALE;

public class Player {

    private Vector2 pos;
    private int hp;
    private int id;
    private PlayerActor actor;

    public Player(int id, Vector2 pos, int hp) {
        pos.scl(MAP_SCALE);
        this.pos = pos;
        this.hp = hp;
        this.id = id;
    }

    public void setActor(PlayerActor actor) {
        this.actor = actor;
    }

    public int getHp() {
        return hp;
    }

    public int getId() {
        return id;
    }

    public Vector2 getPos() {
        return pos;
    }
}
