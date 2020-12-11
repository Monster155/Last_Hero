package Old.Lobby;

public class ConnectedUser {
    private String name;
    private String ipAddress;

    public ConnectedUser(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    public String getConnectedUser() {
        return getName() + "&$&@" + getIpAddress();
    }

    public static ConnectedUser setConnectedUser(String data) {
        String[] info = data.split("&$&@");
        return new ConnectedUser(info[1], info[2]);
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
