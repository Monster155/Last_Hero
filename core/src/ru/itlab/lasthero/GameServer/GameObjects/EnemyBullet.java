package ru.itlab.lasthero.GameServer.GameObjects;

import com.badlogic.gdx.math.Vector2;

public class EnemyBullet {

    private Vector2 pos;
    private Vector2 dir;
    private int id;
    private EnemyBulletActor actor;

    public EnemyBullet(int id, Vector2 pos, Vector2 dir) {
        this.id = id;
        this.pos = pos;
        this.dir = dir;
    }

    public void setActor(EnemyBulletActor actor) {
        this.actor = actor;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getDir() {
        return dir;
    }

    public int getId() {
        return id;
    }

    public void delete() {
        actor.delete();
    }
}
