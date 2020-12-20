package GameServer;

import GameServer.Utils.Item;
import GameServer.Utils.Vector2;
import Server.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class User extends Thread {
    private Sender sender;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int userId;
    private String userName;
    private String ip;

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
        try {
            String data;
            while (true) {
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
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        } catch (ClassNotFoundException e) {
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
        int deltaHP = Integer.parseInt(data[0]);
        if (deltaHP < 10)
            hp -= deltaHP;
        sender.sendHP(String.valueOf(hp), userId);
    }

    private void shoot(String data[]) {
        float dirX = Float.parseFloat(data[0]);
        float dirY = Float.parseFloat(data[1]);
        sender.sendShoot(dirX + Protocol.DIVIDER + dirY, userId);
    }

    private void pick(String data[]) {
        int itemId = Integer.parseInt(data[0]);
        //TODO check for item in place
        sender.sendPick(String.valueOf(itemId), userId);
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

    private void sendPersonalMessage(String message) {
        try {
            out.writeObject(sender.PERSONAL + message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
