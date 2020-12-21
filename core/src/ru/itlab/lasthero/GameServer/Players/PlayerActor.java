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
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.GameServer.GameMasks;
import ru.itlab.lasthero.GameServer.GameObjects.BulletActor;
import ru.itlab.lasthero.GameServer.GameObjects.EnemyBulletActor;
import ru.itlab.lasthero.GameServer.Utils.GamePreferences;
import ru.itlab.lasthero.ServerSide.GameDataSender;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SPEED;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.attack_joystick_direction;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.is_attack_key_just_pressed;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.move_joystick_direction;

public class PlayerActor extends Actor {
    private Sprite sprite;
    private Body body;
    private float rotation;
    private OrthographicCamera camera;
    private World world;
    private GameDataSender gds;
    private int hp;
    private float shootRate;
    private float shootTimer;
    //TODO change at style

    public PlayerActor(Player player, World world, OrthographicCamera camera) {
        player.setActor(this);
        shootRate = 1;
        shootTimer = 0;
        hp = player.getHp();
        this.world = world;
        setBounds(player.getPos().x, player.getPos().y, GamePreferences.CHARACTER_SIZE, GamePreferences.CHARACTER_SIZE);
        createSprite();
        createBody(world);
        this.camera = camera;
        move();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        move();
        body.setLinearVelocity(move_joystick_direction.x * delta * CHARACTER_SPEED,
                move_joystick_direction.y * delta * CHARACTER_SPEED);

        rotation = (float) Math.atan2(attack_joystick_direction.x, -attack_joystick_direction.y);
        sprite.setRotation(rotation * 57.3f);

        shootTimer -= delta;
        //if pistol
        if (is_attack_key_just_pressed && shootTimer <= 0) {
            shoot();
            shootTimer = shootRate;
        }
        //if rifle
//        if (is_attack_key_pressed && shootTimer <= 0) {
//            shootTimer = shootRate;
//        }
    }

    public void getDamage(EnemyBulletActor e) {
        hp -= e.getDamage();
        e.setDestroy(true);
        gds.getDamage(hp, e.getOwnerId());
    }

    private void shoot() {
        getStage().addActor(new BulletActor(getCenterPos(), getADir(), world));
        //TODO change damage to gun style
        gds.shoot(getCenterPos(), getADir());
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
        shape.setRadius(getWidth() / 2);

        Fixture fixture = body.createFixture(shape, 1);
        fixture.setUserData(this);
        Filter filter = new Filter();
        filter.categoryBits = GameMasks.PLAYER_C;
        filter.maskBits = GameMasks.PLAYER_M;
        fixture.setFilterData(filter);
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

    public Vector2 getMDir() {
        return move_joystick_direction;
    }

    public Vector2 getADir() {
        return new Vector2(attack_joystick_direction.x, attack_joystick_direction.y);
    }

    public void setGds(GameDataSender gds) {
        this.gds = gds;
    }
}
