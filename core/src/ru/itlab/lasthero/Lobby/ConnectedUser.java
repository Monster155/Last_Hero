package ru.itlab.lasthero.Lobby;

import ru.itlab.lasthero.Protocol;

public class ConnectedUser {
    private String name;
    private String ipAddress;

    public ConnectedUser(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public String getConnectedUser() {
        return getName() + Protocol.DIVIDER + getIpAddress();
    }

    public static ConnectedUser setConnectedUser(String data) {
        String[] info = data.split(Protocol.DIVIDER);
//        return new ConnectedUser(info[0], info[1]);
        return new ConnectedUser("Player 1", "1236732.2346");
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
