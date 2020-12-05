package ru.itlab.lasthero.Lobby;

import ru.itlab.lasthero.Protocol;

public class OtherUser {
    private String name;
    private String ipAddress;
    private int idInConnectedRoom;

    public OtherUser(String name, String ipAddress, String idInConnectedRoom) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.idInConnectedRoom = Integer.parseInt(idInConnectedRoom);
    }

    public String getConnectedUser() {
        return getName() + Protocol.DIVIDER + getIpAddress() + Protocol.DIVIDER + getIdInConnectedRoom();
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getIdInConnectedRoom() {
        return idInConnectedRoom;
    }
}
