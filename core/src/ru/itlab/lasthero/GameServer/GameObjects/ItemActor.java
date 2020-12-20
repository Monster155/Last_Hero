package ru.itlab.lasthero.GameServer.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.ITEM_SIZE;

public class ItemActor extends Actor {

    private Sprite sprite;
    private Item item;

    public ItemActor(Item item) {
        setBounds(item.getPos().x, item.getPos().y, ITEM_SIZE, ITEM_SIZE);
        chooseItem(item.getType().getId());
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    private void chooseItem(int itemId) {
        switch (itemId) {
            // 1 - pistol
            case 1:
//                sprite = new Sprite(new Texture(Gdx.files.internal("")));
                break;
            // 2 - pistol ammo
            case 2:
//                sprite = new Sprite(new Texture(Gdx.files.internal("")));
                break;
            // 3 - rifle
            case 3:
//                sprite = new Sprite(new Texture(Gdx.files.internal("")));
                break;
            // 4 - rifle ammo
            case 4:
//                sprite = new Sprite(new Texture(Gdx.files.internal("")));
                break;
            // 5 - bandage
            case 5:
//                sprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/item/medikit.png")));
                break;
        }
        sprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/item/medikit.png")));
    }

    public void delete() {
        getParent().removeActor(this);
    }
}
