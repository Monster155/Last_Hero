package GameServer;

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

    public Sender(HashMap<Integer, User> users) {
        this.users = users;
        for (User u : users.values()) {
            u.setSender(this);
        }
    }

    public void sendPositionAndDirection(String message, int senderId) {
        sendMessage(POS_AND_DIR + message, senderId);
    }

    public void sendHP(String message, int senderId) {
        sendMessage(HP + message, senderId);
    }

    public void sendShoot(String message, int senderId) {
        sendMessage(SHOOT + message, senderId);
    }

    public void sendPick(String message, int senderId) {
        sendMessage(PICK + message, senderId);
    }

    public void sendHands(String message, int senderId) {
        sendMessage(HANDS + message, senderId);
    }

    public void startGame() {
        sendMessage(START_GAME, -1);
    }

    private void sendMessage(String message, int senderId) {
        for (User u : users.values()) {
            if (u.getUserId() != senderId) {
                u.getMessage(message);
            }
        }
    }
}
