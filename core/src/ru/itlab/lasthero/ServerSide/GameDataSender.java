package ru.itlab.lasthero.ServerSide;

import ru.itlab.lasthero.GameServer.Players.PlayerActor;
import ru.itlab.lasthero.GameServer.Utils.GamePreferences;
import ru.itlab.lasthero.Protocol;

public class GameDataSender extends Thread {

    public static final String START_GAME = "10";
    private final String POS_AND_DIR = "01";
    private final String HP = "02";
    private final String SHOOT = "03";
    private final String PICK = "04";
    private final String HANDS = "05";
    private final float TICK_RATE = 1 / 64f * 1000;
    private long lastTime;
    private PlayerActor player;

    public GameDataSender(PlayerActor player) {
        this.player = player;
        player.setGds(this);
        lastTime = System.currentTimeMillis();
        start();
    }

    @Override
    public void run() {
        while (true) {
            if (System.currentTimeMillis() - lastTime >= TICK_RATE) {
                lastTime = System.currentTimeMillis();
                Connector.here.sendMessage(POS_AND_DIR + player.getPos().x + Protocol.DIVIDER
                        + player.getPos().y + Protocol.DIVIDER + GamePreferences.move_joystick_direction.x
                        + Protocol.DIVIDER + GamePreferences.move_joystick_direction.y);
            }
        }
    }

    public void taken(int itemId) {
        if (itemId == -1) return;
        Connector.here.sendMessage(PICK + player.getPos().x + Protocol.DIVIDER + player.getPos().y + Protocol.DIVIDER + itemId);
    }
}
