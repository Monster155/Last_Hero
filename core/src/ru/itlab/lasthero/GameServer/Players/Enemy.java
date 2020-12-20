package ru.itlab.lasthero.GameServer.Players;

import com.badlogic.gdx.math.Vector2;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.MAP_SCALE;

public class Enemy {
    private EnemyActor actor;
    private Vector2 pos;
    private Vector2 dir;
    private int hp;
    private int id;

    public Enemy(Vector2 pos, int hp, int id) {
        pos.scl(MAP_SCALE);
        this.pos = pos;
        this.hp = hp;
        this.id = id;
        dir = new Vector2(0, 0);
    }

    public void updatePosAndDir(Vector2 pos, Vector2 dir) {
        this.pos = pos;
        this.dir = dir;
        actor.updatePos();
    }

    public void setActor(EnemyActor actor) {
        this.actor = actor;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getDir() {
        return dir;
    }

    public int getHp() {
        return hp;
    }

    public int getId() {
        return id;
    }

    public void delete() {
        actor.delete();
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "pos=" + pos +
                ", dir=" + dir +
                ", hp=" + hp +
                ", id=" + id +
                '}';
    }
}
