package LobbyList;


import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private final int MAX_COUNT_OF_USERS = 10;
    private HashMap<Integer, User> connectedUsers;
    private int usersCount;
    private int id;
    private String name;
    private String ip;
    private boolean isInGame;
    private ArrayList<Boolean> usedIds;
    private ArrayList<User> users;

    public Room(String name, String ip, int id, ArrayList<User> users) {
        this.name = name;
        this.ip = ip;
        this.id = id;
        this.users = users;
        connectedUsers = new HashMap<>();
        usersCount = 0;
        usedIds = new ArrayList<>();
        showNewRoom();
    }

    public int getMAX_COUNT_OF_USERS() {
        return MAX_COUNT_OF_USERS;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    private void showNewRoom() {
        for (User u : users) {
            u.acceptNewRoom(this);
        }
    }

    public void removeRoom() {
        for (User u : users) {
            u.removeRoom(this);
        }
    }


    public boolean connectUser(User user) {
        if (usersCount >= MAX_COUNT_OF_USERS) {
            return false;
        }
        usersCount++;
        user.setId(getUserIdInRoom());
        connectedUsers.put(user.getUserId(), user);
        users.remove(user);
        update(user, true);
        return true;
    }

    public boolean disconnectUser(User user) {
        usersCount--;
        removeUserId(user);
        connectedUsers.remove(user);
        users.add(user);
        update(user, false);
        return true;
    }

    public void update(User user, boolean isNew) {
        for (User u : users) {
            u.roomUpd(getId(), getUsersCount());
        }
        for (User u : connectedUsers.values()) {
            u.roomUpd(user, isNew);

        }
    }

    private int getUserIdInRoom() {
        int freeID = -1;
        for (int i = 0; i < usedIds.size(); i++) {
            if (!usedIds.get(i)) {
                freeID = i + 1;
                usedIds.set(i, true);
            }
        }
        if (freeID == -1) {
            usedIds.add(true);
            freeID = usedIds.size();
        }
        return freeID;
    }

    private void removeUserId(User user) {
        usedIds.set(user.getUserId(), false);
    }

    public int getUsersCount() {
        return usersCount;
    }
}
