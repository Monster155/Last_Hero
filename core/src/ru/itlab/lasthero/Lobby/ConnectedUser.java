package ru.itlab.lasthero.Lobby;

public class ConnectedUser {
    private String name;
    private String ipAddress;

    public ConnectedUser(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public String getConnectedUser() {
        return getName() + "&" + getIpAddress();
    }

    public static ConnectedUser setConnectedUser(String data) {
        String[] info = data.split("&");
        return new ConnectedUser(info[0], info[1]);
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
