package Old.NewLobby;

import Server.Protocol;

import java.util.ArrayList;

public class Room {
    private String name;
    private String ip;
    private int roomId;
    private int playersCount;
    private int maxPlayersCount;
    private ArrayList<User> users;
    private ArrayList<Boolean> usedIDs;
    private boolean isFinish;
    private boolean isUpdated;

    public Room(String name, String ip, int roomId) {
        users = new ArrayList<User>();
        usedIDs = new ArrayList<Boolean>();
        this.roomId = roomId;
        this.name = name;
        this.ip = ip;
        this.playersCount = 0;
        this.maxPlayersCount = 10; //TODO make maxPlayersCount global
    }

    public String compressRoomInfo() {
        String data[] = {getRoomId() + "", getName(), getIp(), getPlayersCount() + "", getMaxPlayersCount() + ""};
        StringBuilder sb = new StringBuilder();
        for (String str : data) {
            sb.append(str).append(Protocol.DIVIDER);
        }
        return sb.toString();
    }

    public String getConnectedUsersInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(playersCount).append(Protocol.DIVIDER);
        for (User user : users) {
            sb.append("1").append(Protocol.DIVIDER).append(user.compressInfo()).append(Protocol.DIVIDER);
        }
        return sb.toString();
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

    public boolean isFinish() {
        return isFinish;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public boolean addNewUser(User user) {
        if (playersCount >= maxPlayersCount) {
            return false;
        }
        playersCount++;
        for (User u : users) {
            u.updateConnectedRoomInfo(true, user, playersCount);
        }
        // user попадает в массив позже, т.к. сначала у него отрабатывает
        // логика подключения к комнате, а потом уже запрос на других игроков
        users.add(user);
        user.setIdInConnectedRoom(getUserIDInRoom());
        return true;
    }

    public void disconnectUser(User user) {
        playersCount--;
        users.remove(user);
        for (User u : users) {
            u.updateConnectedRoomInfo(false, user, playersCount);
        }
        user.setIdInConnectedRoom(-1);
    }

    public int getRoomId() {
        return roomId;
    }

    private int getUserIDInRoom() {
        int freeID = -1;
        for (int i = 0; i < usedIDs.size(); i++) {
            if (!usedIDs.get(i)) {
                freeID = i + 1;
                usedIDs.set(i, true);
            }
        }
        if (freeID == -1) {
            usedIDs.add(true);
            freeID = usedIDs.size();
        }
        return freeID;
    }
}
