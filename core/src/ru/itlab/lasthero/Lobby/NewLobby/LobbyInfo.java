package ru.itlab.lasthero.Lobby.NewLobby;

import ru.itlab.lasthero.Protocol;

public class LobbyInfo {
    private String name;
    private String ip;
    private int playersCount;
    private int maxPlayersCount;

    public LobbyInfo(String name, String ip, int playersCount, int maxPlayersCount) {
        this.name = name;
        this.ip = ip;
        this.playersCount = playersCount;
        this.maxPlayersCount = maxPlayersCount;
    }

    public static LobbyInfo getLobbyInfo(String data) {
        String info[] = data.split(Protocol.DIVIDER);
        return new LobbyInfo(info[0], info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]));
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
}
