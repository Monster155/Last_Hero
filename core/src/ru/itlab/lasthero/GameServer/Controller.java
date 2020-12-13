package ru.itlab.lasthero.GameServer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static ru.itlab.lasthero.GameServer.GamePreferences.BASE_SCREEN_SIZE;

public class Controller extends Actor {

    private ModuleID moduleID;
    private boolean isMobile;

    public Controller(ModuleID moduleID) {
        this.moduleID = moduleID;
        System.out.println("Controller: Module ID: " + moduleID);
        switch (moduleID) {
            case ANDROID://android
            case IOS://ios
                isMobile = true;
                break;
            case DESKTOP://desktop
            case HTML://html
                isMobile = false;
                break;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isMobile) {
            // draw mobile controllers
        }
        // PC and HTML controller doesn't draw
    }

    @Override
    public void act(float delta) {
        Vector2 moveDirection = new Vector2(0, 0);
        Vector2 attackDirection = new Vector2(0, 0);
        if (isMobile) {
            // act mobile controllers
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
                moveDirection.y = 1;
            if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
                moveDirection.y = -1;

            if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                moveDirection.x = 1;
            if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
                moveDirection.x = -1;

            if (moveDirection.x != 0 && moveDirection.y != 0) {
                moveDirection.x /= 1.4f;
                moveDirection.y /= 1.4f;
            }
            attackDirection = new Vector2(Gdx.input.getX() - BASE_SCREEN_SIZE.x / 2, Gdx.input.getY() - BASE_SCREEN_SIZE.y / 2);
            //find scaled coordinates (not need now)
//            float scale = (float) Math.sqrt(attackDirection.x * attackDirection.x + attackDirection.y * attackDirection.y);
//            attackDirection.x = attackDirection.x / scale;
//            attackDirection.y = attackDirection.y / scale;
        }
        GamePreferences.move_joystick_direction = moveDirection;
        GamePreferences.attack_joystick_direction = attackDirection;
    }

    public enum ModuleID {
        ANDROID,
        DESKTOP,
        HTML,
        IOS
    }
}
