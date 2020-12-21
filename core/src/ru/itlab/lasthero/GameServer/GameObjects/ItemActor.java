package ru.itlab.lasthero.GameServer.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.GameServer.Players.PlayerActor;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.ITEM_SIZE;

public class ItemActor extends Actor {

    private Sprite sprite;
    private Item item;
    private Rectangle rect;
    private PlayerActor player;

    public ItemActor(Item item, PlayerActor player) {
        this.player = player;
        this.item = item;
        setBounds(item.getPos().x, item.getPos().y, ITEM_SIZE, ITEM_SIZE);
        chooseItem(item.getType().getId());
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        rect = new Rectangle(getX() - 2 * getWidth(), getY() - 2 * getHeight(), getWidth() * 5, getHeight() * 5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        if (rect.contains(player.getCenterPos())) {
            player.setReadyItem(item.getId());
        } else if (player.getReadyItem() == item.getId()) {
            player.setReadyItem(-1);
        }
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
        getStage().getActors().removeValue(this, true);
    }
}
