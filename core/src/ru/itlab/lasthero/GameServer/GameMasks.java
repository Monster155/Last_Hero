package ru.itlab.lasthero.GameServer;

public class GameMasks {
    public static final short PLAYER_C = 0x0001;
    public static final short P_BULLET_C = 0x0002;
    public static final short ENEMY_C = 0x0004;
    public static final short E_BULLET_C = 0x0008;
    public static final short WORLD_C = 0x0010;

    public static final short PLAYER_M = E_BULLET_C | WORLD_C;
    public static final short P_BULLET_M = WORLD_C;
    public static final short ENEMY_M = WORLD_C;
    public static final short E_BULLET_M = PLAYER_C | WORLD_C;
    public static final short WORLD_M = -1;
}
