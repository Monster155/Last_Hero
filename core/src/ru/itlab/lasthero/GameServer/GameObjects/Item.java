package ru.itlab.lasthero.GameServer.GameObjects;

import com.badlogic.gdx.math.Vector2;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.MAP_SCALE;

public class Item {

    private Vector2 pos;
    private int id;
    private ItemType type;
    private ItemActor actor;
    // 1 - pistol
    // 2 - pistol ammo
    // 3 - rifle
    // 4 - rifle ammo
    // 5 - bandage

    public Item(Vector2 pos, int itemId, int id) {
        pos.scl(MAP_SCALE);
        this.pos = pos;
        this.id = id;
        chooseType(itemId);
    }

    public int getId() {
        return id;
    }

    public Vector2 getPos() {
        return pos;
    }

    public ItemType getType() {
        return type;
    }

    private void chooseType(int id) {
        switch (id) {
            // 1 - pistol
            case 1:
                type = ItemType.PISTOL;
                break;
            // 2 - pistol ammo
            case 2:
                type = ItemType.PISTOL_AMMO;
                break;
            // 3 - rifle
            case 3:
                type = ItemType.RIFLE;
                break;
            // 4 - rifle ammo
            case 4:
                type = ItemType.RIFLE_AMMO;
                break;
            // 5 - bandage
            case 5:
                type = ItemType.BANDAGE;
                break;
        }
    }

    public void delete() {
        actor.delete();
    }

    @Override
    public String toString() {
        return "Item{" +
                "x=" + getPos().x +
                ", y=" + getPos().y +
                ", id=" + id +
                ", type=" + type +
                '}';
    }

    public void setActor(ItemActor actor) {
        this.actor = actor;
    }

    enum ItemType {
        PISTOL(1),
        PISTOL_AMMO(2),
        RIFLE(3),
        RIFLE_AMMO(4),
        BANDAGE(5);

        private final int id;

        ItemType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
