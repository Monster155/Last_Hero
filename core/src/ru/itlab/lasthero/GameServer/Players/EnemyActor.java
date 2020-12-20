package ru.itlab.lasthero.GameServer.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SIZE;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SPEED;

public class EnemyActor extends Actor {

    private Enemy enemy;
    private Sprite sprite;

    public EnemyActor(Enemy enemy) {
        this.enemy = enemy;
        System.out.println("Enemy actor for " + enemy.getId() + " created");
        setBounds(enemy.getPos().x, enemy.getPos().y, CHARACTER_SIZE, CHARACTER_SIZE);
        sprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/characters/example.png")));
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
        sprite.setOriginCenter();
        sprite.setRotation(0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
//        System.out.println(enemy.getId());
    }

    @Override
    public void act(float delta) {
        setPosition(getX() + enemy.getDir().x * delta * CHARACTER_SPEED,
                getY() + enemy.getDir().y * delta * CHARACTER_SPEED);
        move();
    }

    public void updatePos() {
        setBounds(enemy.getPos().x, enemy.getPos().y, CHARACTER_SIZE, CHARACTER_SIZE);
        move();
    }

    private void move() {
        sprite.setBounds(getX(), getY(), getWidth(), getHeight());
    }

    public void delete() {
        getParent().removeActor(this);
    }
}
