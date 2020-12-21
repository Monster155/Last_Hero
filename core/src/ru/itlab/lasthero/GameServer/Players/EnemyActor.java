package ru.itlab.lasthero.GameServer.Players;

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

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SIZE;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SPEED;

public class EnemyActor extends Actor {

    private Enemy enemy;
    private Sprite sprite;
    private Body body;
    private boolean hasFromNet;

    public EnemyActor(Enemy enemy, World world) {
        this.enemy = enemy;
        hasFromNet = false;
        System.out.println("Enemy actor for " + enemy.getId() + " created");
        setBounds(enemy.getPos().x, enemy.getPos().y, CHARACTER_SIZE, CHARACTER_SIZE);
        sprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/characters/example.png")));
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOriginCenter();
        sprite.setRotation(0);
        createBody(world);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
//        System.out.println(enemy.getId());
    }

    @Override
    public void act(float delta) {
        System.out.println(enemy.getDir());
        body.setLinearVelocity(enemy.getDir().x * delta * CHARACTER_SPEED,
                enemy.getDir().y * delta * CHARACTER_SPEED);
        move();
    }

    public void updatePos() {
        if (body.getTransform().getPosition().epsilonEquals(enemy.getPos(), 4))
            return;
        body.setTransform(enemy.getPos(), 0);
        //enemy.getPos().y - getY()
        move();
    }

    private void move() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    public void delete() {
        getParent().removeActor(this);
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
        filter.categoryBits = GameMasks.ENEMY_C;
        filter.maskBits = GameMasks.ENEMY_M;
        fixture.setFilterData(filter);
    }
}
