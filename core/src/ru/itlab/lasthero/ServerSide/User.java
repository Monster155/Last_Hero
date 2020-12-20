package ru.itlab.lasthero.ServerSide;

import ru.itlab.lasthero.LobbyServer.Draw.LobbyScreen.UserActor;

public class User {
    private Room connectedRoom;
    private int userId;
    private String ip;
    private String name;
    private UserActor userActor;

    public User(String name, String ip, int userId) {
        this.userId = userId;
        this.ip = ip;
        this.name = name;
    }

    public void addActor(UserActor userActor) {
        this.userActor = userActor;
    }

    public int getUserId() {
        return userId;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public UserActor getUserActor() {
        return userActor;
    }
}
