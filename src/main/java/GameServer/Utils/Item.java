package GameServer.Utils;

import Server.Protocol;

public class Item {
    public static int MAX_ITEMS = 5;
    private Vector2 pos;
    private int itemId;
    private int id;
    // 1 - pistol
    // 2 - pistol ammo
    // 3 - rifle
    // 4 - rifle ammo
    // 5 - bandage

    public Item(Vector2 pos, int itemId, int id) {
        pos.setY(1700 - pos.getY());
        this.pos = pos;
        this.itemId = itemId;
        this.id = id;
    }

    public Vector2 getPos() {
        return pos;
    }

    public int getItemId() {
        return itemId;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return pos.getX() + Protocol.DIVIDER + pos.getY() + Protocol.DIVIDER + getItemId() + Protocol.DIVIDER + id;
    }
}
