package ru.itlab.lasthero.ServerSide;

import java.util.HashMap;

import ru.itlab.lasthero.LobbyServer.Draw.LobbyListScreen.RoomActor;

public class Room {
    private final int MAX_COUNT_OF_USERS;
    private HashMap<Integer, ru.itlab.lasthero.ServerSide.User> connectedUsers;
    private int usersCount;
    private int id;
    private String name;
    private String ip;
    private boolean isInGame;
    private RoomActor roomActor;

    public Room(String name, String ip, int id, int usersCount, int maxUsersCount) {
        this.name = name;
        this.ip = ip;
        this.id = id;
        connectedUsers = new HashMap<>();
        this.usersCount = usersCount;
        MAX_COUNT_OF_USERS = maxUsersCount;
        isInGame = false;
    }

    public void addActor(RoomActor roomActor) {
        this.roomActor = roomActor;
    }

    public void addUser(int id, User user) {
        connectedUsers.put(id, user);
    }

    public void removeUser(int id) {
        connectedUsers.remove(id).getUserActor().delete();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayersCount() {
        return MAX_COUNT_OF_USERS;
    }

    public int getPlayersCount() {
        if (connectedUsers.size() > 0)
            usersCount = connectedUsers.size();
        return usersCount;
    }

    public String getIp() {
        return ip;
    }

    public void setUsersCount(int usersCount) {
        this.usersCount = usersCount;
    }

    public RoomActor getRoomActor() {
        return roomActor;
    }
}
