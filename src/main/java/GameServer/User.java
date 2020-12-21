package GameServer;

import GameServer.Utils.Item;
import GameServer.Utils.Vector2;
import Server.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class User extends Thread {
    private Sender sender;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int userId;
    private String userName;
    private String ip;

    private ArrayList<Item> items;
    private boolean[] takenItems;

    private Vector2 pos;
    private int hp;

    public User(LobbyList.User user) {
        out = user.getOut();
        in = user.getIn();
        ip = user.getIp();
        userName = user.getUserName();
        userId = user.getUserId();
        start();
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public void run() {
        String data;
        boolean shouldStop = false;
        try {
            while (!shouldStop) {
                data = (String) in.readObject();
                int command = Integer.parseInt(data.substring(0, 2));
                String[] info = data.substring(2).split(Protocol.DIVIDER);
                switch (command) {
                    // position and direction
                    case 1:
                        posAndDir(info);
                        break;
                    // hp
                    case 2:
                        hp(info);
                        break;
                    // shoot
                    case 3:
                        shoot(info);
                        break;
                    // pick gun
                    case 4:
                        pick(info);
                        break;
                    // pick medicament
                    case 5:
                        hands(info);
                        break;
                    // player is ready
                    case 10:
                        sender.playerIsReady(userId);
                        break;
                    case 99:
                        shouldStop = true;
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    private void posAndDir(String data[]) {
        float posX = Float.parseFloat(data[0]);
        float posY = Float.parseFloat(data[1]);
        float dirX = Float.parseFloat(data[2]);
        float dirY = Float.parseFloat(data[3]);
        sender.sendPositionAndDirection(posX + Protocol.DIVIDER + posY + Protocol.DIVIDER + dirX + Protocol.DIVIDER + dirY, userId);
    }

    private void hp(String data[]) {
        int hp = Integer.parseInt(data[0]);
        int enemyId = Integer.parseInt(data[1]);
//        if (hp <= 0) //use enemyId and say about kill
        sender.sendHP(String.valueOf(hp), userId);
    }

    private void shoot(String data[]) {
        float posX = Float.parseFloat(data[0]);
        float posY = Float.parseFloat(data[1]);
        float dirX = Float.parseFloat(data[2]);
        float dirY = Float.parseFloat(data[3]);
        sender.sendShoot(posX + Protocol.DIVIDER + posY + Protocol.DIVIDER
                + dirX + Protocol.DIVIDER + dirY, userId);
    }

    private void pick(String data[]) {
        float x = Float.parseFloat(data[0]);
        float y = Float.parseFloat(data[1]);
        int itemId = Integer.parseInt(data[2]);
        System.out.println(Math.abs(items.get(itemId).getPos().getX() - x));
        System.out.println(Math.abs(items.get(itemId).getPos().getY() - y));
        if (Math.abs(items.get(itemId).getPos().getX() - x) < 10
                && Math.abs(items.get(itemId).getPos().getY() - y) < 10) {
            if (takenItems[itemId]) return;
            System.out.println(itemId + " taken");
            sender.sendPick(String.valueOf(itemId), userId);
            takenItems[itemId] = true;
//            sender.endGame();
        }
        boolean isAllTaken = true;
        for (boolean isTaken : takenItems) {
            if (!isTaken) {
                isAllTaken = false;
                break;
            }
        }
        if (isAllTaken) {
            sender.endGame();
        }
    }

    private void hands(String data[]) {
        int handsId = Integer.parseInt(data[0]);
        int itemId = Integer.parseInt(data[1]);
        sender.sendHands(String.valueOf(itemId), userId);
    }

    public void getMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getCause() + " : " + e.getCause());
        }
    }

    public void setStats(Vector2 pos, int hp, ArrayList<Item> items) {
        this.items = items;
        takenItems = new boolean[items.size()];
        for (int i = 0; i < takenItems.length; i++) {
            takenItems[i] = false;
        }
        this.pos = pos;
        this.hp = hp;
        // 01 - self
        // 02 - items
        StringBuilder sb = new StringBuilder();
        sb.append(userId).append(Protocol.DIVIDER).append(pos.getX()).append(Protocol.DIVIDER).append(pos.getY()).append(Protocol.DIVIDER).append(hp).append(Protocol.DIVIDER);
        for (Item item : items) {
            sb.append(item.toString()).append(Protocol.DIVIDER);
        }
        sendPersonalMessage(sb.toString());
    }

    public void spawnEnemies(HashMap<Integer, User> users) {
        StringBuilder sb = new StringBuilder();
        for (User u : users.values()) {
            if (u != this) {
                sb.append(u.pos.getX()).append(Protocol.DIVIDER);
                sb.append(u.pos.getY()).append(Protocol.DIVIDER);
                sb.append(u.hp).append(Protocol.DIVIDER);
                sb.append(u.userId).append(Protocol.DIVIDER);
            }
        }
        try {
            out.writeObject(sender.USERS + sb.toString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPersonalMessage(String message) {
        try {
            out.writeObject(sender.PERSONAL + message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }
}
