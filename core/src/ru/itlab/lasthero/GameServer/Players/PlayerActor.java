package ru.itlab.lasthero.GameServer.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.GameServer.Utils.GamePreferences;
import ru.itlab.lasthero.ServerSide.GameDataSender;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SIZE;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SPEED;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.SPEED;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.attack_joystick_direction;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.is_take;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.move_joystick_direction;

public class PlayerActor extends Actor {
    private Sprite sprite;
    private Body body;
    private float rotation;
    private OrthographicCamera camera;
    private GameDataSender gds;
    private int readyItem;

    public PlayerActor(Player player, World world, OrthographicCamera camera) {
        player.setActor(this);
        setBounds(player.getPos().x, player.getPos().y, GamePreferences.CHARACTER_SIZE, GamePreferences.CHARACTER_SIZE);
        createSprite();
        createBody(world);
        this.camera = camera;
        move();
        readyItem = -1;
    }

    public void setGds(GameDataSender gds) {
        this.gds = gds;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        move();
        body.setLinearVelocity(SPEED.x * move_joystick_direction.x * delta * CHARACTER_SPEED,
                SPEED.y * move_joystick_direction.y * delta * CHARACTER_SPEED);

        rotation = (float) Math.atan2(attack_joystick_direction.x, attack_joystick_direction.y);
        sprite.setRotation(rotation * 57.3f);

        if (is_take) {
            System.out.println("sent");
            gds.taken(readyItem);
        }
    }

    public int getReadyItem() {
        return readyItem;
    }

    public void setReadyItem(int readyItem) {
        this.readyItem = readyItem;
    }

    private void createSprite() {
        sprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/characters/tank.png")));
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        rotation = 0;
        sprite.setOriginCenter();
        sprite.setRotation(rotation);
    }

    private void createBody(World world) {
        BodyDef def = new BodyDef();
        def.position.set(getX(), getY());
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        def.angle = rotation * 0.0175f;

        body = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setPosition(new Vector2(0, 0));
        shape.setRadius(CHARACTER_SIZE / 2);

        body.createFixture(shape, 1);
//        Fixture fixture = body.createFixture(shape, 1);
//        Filter filter = new Filter();
//        filter.groupIndex = -1;
//        fixture.setFilterData(filter);
    }

    private void move() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        sprite.setPosition(getX(), getY());
        camera.position.set(body.getPosition(), camera.position.z);
        camera.update();
    }

    public Vector2 getPos() {
        return new Vector2(getX(), getY());
    }

    public Vector2 getCenterPos() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }
}
