package GameServer;

import Server.Protocol;

import java.util.HashMap;

public class Sender {
    public final String PERSONAL = "00";
    public final String USERS = "06";
    private final String POS_AND_DIR = "01";
    private final String HP = "02";
    private final String SHOOT = "03";
    private final String PICK = "04";
    private final String HANDS = "05";
    private final String START_GAME = "10";
    private HashMap<Integer, User> users;
    private boolean[] readyPlayersCount;

    public Sender(HashMap<Integer, User> users) {
        readyPlayersCount = new boolean[users.size()];
        this.users = users;
        for (User u : users.values()) {
            u.setSender(this);
        }
    }

    public void playerIsReady(int id) {
        readyPlayersCount[id] = true;
        for (boolean b : readyPlayersCount) {
            if (!b) return;
        }
        startGame();
    }

    public void sendPositionAndDirection(String message, int senderId) {
        sendMessage(POS_AND_DIR + senderId + Protocol.DIVIDER + message, senderId);
    }

    public void sendHP(String message, int senderId) {
        sendMessage(HP + senderId + Protocol.DIVIDER + message, senderId);
    }

    public void sendShoot(String message, int senderId) {
        sendMessage(SHOOT + senderId + Protocol.DIVIDER + message, senderId);
    }

    public void sendPick(String message, int senderId) {
        sendMessage(PICK + senderId + Protocol.DIVIDER + message, senderId);
    }

    public void sendHands(String message, int senderId) {
        sendMessage(HANDS + senderId + Protocol.DIVIDER + message, senderId);
    }

    public void startGame() {
        sendMessage(START_GAME, -1);
        System.out.println("Game started");
    }

    private void sendMessage(String message, int senderId) {
        for (User u : users.values()) {
            if (u.getUserId() != senderId) {
                u.getMessage(message);
            }
        }
    }
}
