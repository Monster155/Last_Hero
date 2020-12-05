package ru.itlab.lasthero.NewLobby;

import java.util.HashMap;

import ru.itlab.lasthero.Lobby.OtherUser;
import ru.itlab.lasthero.Protocol;

public class Room {
    private String name;
    private String ip;
    private int roomId;
    private int playersCount;
    private int maxPlayersCount;
    private HashMap<Integer, OtherUser> users;
    private boolean isInGame;

    public Room(int roomId, String name, String ip) {
        this.roomId = roomId;
        users = new HashMap<>();
        this.name = name;
        this.ip = ip;
        this.playersCount = 0;
        this.maxPlayersCount = 10; //TODO make maxPlayersCount global
        isInGame = false;
    }

    public Room(int roomId, String name, String ip, int playersCount, int maxPlayersCount) {
        this(roomId, name, ip);
        this.playersCount = playersCount;
        this.maxPlayersCount = maxPlayersCount;
    }

    public static Room decompressRoomInfo(String data) {
        String info[] = data.split(Protocol.DIVIDER);
        return new Room(Integer.parseInt(info[0]), info[1], info[2], Integer.parseInt(info[3]), Integer.parseInt(info[4]));
    }

    public void updateRoom(String data) {
        String info[] = data.split(Protocol.DIVIDER);
        if (!info[0].equals(roomId + "")) return;
        updateInfo(info[1], info[2]);
    }

    public void updateConnectedRoom(String data) {
        String info[] = data.split(Protocol.DIVIDER);
        playersCount = Integer.parseInt(info[0]);
        for (int i = 1; i < info.length; ) {
            // 1 - add, 2 - remove
            if (info[i++].equals("1")) {
                OtherUser otherUser = new OtherUser(info[i++], info[i++], info[i++]);
                users.put(otherUser.getIdInConnectedRoom(), otherUser);
            } else {
                users.remove(Integer.parseInt(info[i++]));
            }
        }
    }

    private void updateInfo(String playersCount, String isInGame) {
        this.playersCount = Integer.parseInt(playersCount);
        this.isInGame = Boolean.parseBoolean(isInGame);
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public int getMaxPlayersCount() {
        return maxPlayersCount;
    }

    public int getRoomId() {
        return roomId;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public HashMap<Integer, OtherUser> getUsers() {
        return users;
    }
}
