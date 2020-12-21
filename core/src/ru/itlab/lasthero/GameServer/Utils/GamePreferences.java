package ru.itlab.lasthero.GameServer.Utils;

import com.badlogic.gdx.math.Vector2;

public class GamePreferences {
    public static final int CHARACTER_SIZE = 10;
    public static final float CHARACTER_SPEED = 5000;
    public static final float BULLET_SPEED = 10000;
    public static final int ITEM_SIZE = 5;
    public static final int MAP_SCALE = 1;
    public static final Vector2 BASE_SCREEN_SIZE = new Vector2(640, 360);
    public static boolean is_attack_key_pressed = false;
    public static boolean is_attack_key_just_pressed = false;
    public static Vector2 move_joystick_direction = new Vector2(0, 0);
    public static Vector2 attack_joystick_direction = new Vector2(0, 0);
    public static boolean is_take = false;
}
