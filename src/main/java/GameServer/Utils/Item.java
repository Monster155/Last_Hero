package GameServer.Utils;

import Server.Protocol;

public class Item {
    private Vector2 pos;
    private int id;
    private int posId;
    public static int MAX_ITEMS = 5;
    // 1 - pistol
    // 2 - pistol ammo
    // 3 - rifle
    // 4 - rifle ammo
    // 5 - bandage

    public Item(Vector2 pos, int id, int posId) {
        this.pos = pos;
        this.id = id;
        this.posId = posId;
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return pos.getX() + Protocol.DIVIDER + pos.getY() + Protocol.DIVIDER + getId() + Protocol.DIVIDER + posId;
    }
}
