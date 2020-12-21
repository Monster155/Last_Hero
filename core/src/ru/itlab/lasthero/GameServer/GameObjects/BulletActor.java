package ru.itlab.lasthero.GameServer.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.GameServer.GameMasks;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.BULLET_SPEED;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SIZE;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.ITEM_SIZE;

public class BulletActor extends Actor {

    private Body body;
    private Sprite sprite;
    private Vector2 dir;
    private float flyTime;
    private boolean isDestroy;
    private boolean destroyOnce;

    public BulletActor(Vector2 pos, Vector2 dir, World world) {
        this.dir = dir;
        isDestroy = false;
        destroyOnce = false;
        flyTime = 3;
        setBounds(pos.x + dir.x * CHARACTER_SIZE, pos.y + dir.y * CHARACTER_SIZE, ITEM_SIZE, ITEM_SIZE);
        createBody(world);
        sprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/weapons/shoot/9.png")));
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        body.setLinearVelocity(dir.x * delta * BULLET_SPEED, dir.y * delta * BULLET_SPEED);
        move();
        flyTime -= delta;
        if (flyTime <= 0) {
            delete();
            destroy();
        }
    }

    private void createBody(World world) {
        BodyDef def = new BodyDef();
        def.position.set(getX(), getY());
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.angle = 0;

        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(getWidth() / 2);

        Fixture fixture = body.createFixture(shape, 1);
        fixture.setUserData(this);
        Filter filter = new Filter();
        filter.categoryBits = GameMasks.P_BULLET_C;
        filter.maskBits = GameMasks.P_BULLET_M;
        fixture.setFilterData(filter);
    }

    private void move() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        sprite.setPosition(getX(), getY());
    }

    public void delete() {
        if (isDestroy) return;
        getParent().removeActor(this);
        isDestroy = true;
    }

    public boolean isDestroy() {
        return isDestroy;
    }

    public void setDestroy(boolean destroy) {
        isDestroy = destroy;
    }

    public void destroy() {
        if (destroyOnce) return;
        body.setActive(false);
        body.getWorld().destroyBody(body);
        destroyOnce = true;
    }
}
