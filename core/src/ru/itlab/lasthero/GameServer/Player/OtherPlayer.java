package ru.itlab.lasthero.GameServer.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static ru.itlab.lasthero.GameServer.GamePreferences.CHARACTER_SIZE;
import static ru.itlab.lasthero.GameServer.GamePreferences.SPEED;
import static ru.itlab.lasthero.GameServer.GamePreferences.attack_joystick_direction;
import static ru.itlab.lasthero.GameServer.GamePreferences.move_joystick_direction;

public class OtherPlayer extends Actor {
    private Sprite bodySprite;
    private OrthographicCamera camera;
    private float hp;

    public OtherPlayer(Vector2 pos) {
        setBounds(pos.x, pos.y, CHARACTER_SIZE.x, CHARACTER_SIZE.y);
        bodySprite = new Sprite(new Texture(Gdx.files.internal("top-down-shooter-1/characters/example.png")));
        bodySprite.setBounds(getX(), getY(), getWidth(), getHeight());
        bodySprite.setOriginCenter();
        hp = 100;
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
}
