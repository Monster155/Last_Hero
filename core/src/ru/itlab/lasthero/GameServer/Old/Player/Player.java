package ru.itlab.lasthero.GameServer.Old.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import ru.itlab.lasthero.Protocol;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.CHARACTER_SIZE;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.SPEED;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.attack_joystick_direction;
import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.move_joystick_direction;

public class Player extends Actor {

    private Sprite bodySprite;
    private OrthographicCamera camera;
    private int id;
    private float hp;

    public Player(OrthographicCamera camera) {
        setBounds(0, 0, CHARACTER_SIZE, CHARACTER_SIZE);
        bodySprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/characters/example.png")));
        bodySprite.setBounds(getX(), getY(), getWidth(), getHeight());
        bodySprite.setOriginCenter();
        this.camera = camera;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        bodySprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        //move
        setPosition(getX() + SPEED.x * move_joystick_direction.x * delta,
                getY() + SPEED.y * move_joystick_direction.y * delta);
        bodySprite.setPosition(getX(), getY());
        camera.position.set(getX() + getWidth() / 2, getY() + getHeight() / 2, camera.position.z);
        camera.update();
        //rotation
        bodySprite.setRotation((float) (Math.atan2(attack_joystick_direction.x, attack_joystick_direction.y) * 57.3f));
    }

    public String getData() {
        //TODO it's not socket class
        return id + Protocol.DIVIDER + getX() + Protocol.DIVIDER + getY() + Protocol.DIVIDER + hp;
    }
}
