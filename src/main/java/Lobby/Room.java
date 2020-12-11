package Lobby;


import java.util.ArrayList;

public class Room {
    private String name;
    private String ip;
    private int roomId;
    private int playersCount;
    private int maxPlayersCount;
    private ArrayList<User> users;
    private ArrayList<Boolean> usedIDs;

    public Room(String name, String ip, int roomId) {
        users = new ArrayList<User>();
        usedIDs = new ArrayList<Boolean>();
        this.roomId = roomId;
        this.name = name;
        this.ip = ip;
        this.playersCount = 0;
        this.maxPlayersCount = 10; //TODO make maxPlayersCount global
    }

    public boolean addNewUser(User user) {
        if (playersCount >= maxPlayersCount) {
            return false;
        }
        user.setId(getUserId());
        playersCount++;
        for (User u : users) {
            //TODO update connected room
        }
        users.add(user);
        return true;
    }

    public void disconnectUser(User user) {
        playersCount--;
        removeUserId(user);
        users.remove(user);
        for (User u : users) {
            //TODO update connected room
        }
        user.setId(-1);
    }

    private int getUserId() {
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

    private void removeUserId(User user){
        usedIDs.set(user.getId()-1, false);
    }
}
