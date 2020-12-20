package LobbyList;


import GameServer.GameRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class Room {
    private final int MAX_COUNT_OF_USERS = 1;
    private final int TIME_BEFORE_START = 5000;
    private HashMap<Integer, User> connectedUsers;
    private int usersCount;
    private int id;
    private String name;
    private String ip;
    private boolean isInGame;
    private ArrayList<Boolean> usedIds;
    private ArrayList<User> users;
    private Timer timer;
    private GameRoom gameRoom;

    public Room(String name, String ip, int id, ArrayList<User> users) {
        this.name = name;
        this.ip = ip;
        this.id = id;
        this.users = users;
        connectedUsers = new HashMap<>();
        usersCount = 0;
        usedIds = new ArrayList<>();
        isInGame = false;
        timer = new Timer();
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
        users.remove(user);
        update(user, true);
        connectedUsers.put(user.getUserId(), user);
        return true;
    }

    public void showUsers(User user) {
        for (User u : connectedUsers.values()) {
            user.roomUpd(u, true);
        }
    }

    public boolean disconnectUser(User user) {
        usersCount--;
        removeUserId(user);
        connectedUsers.remove(user.getUserId());
        users.add(user);
        update(user, false);
        if (isInGame) {
            isInGame = false;
            stopPrepareToStart();
        }
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
                freeID = i;
                usedIds.set(i, true);
                break;
            }
        }
        if (freeID == -1) {
            freeID = usedIds.size();
            usedIds.add(true);
        }
        return freeID;
    }

    private void removeUserId(User user) {
        usedIds.set(user.getUserId(), false);
    }

    public int getUsersCount() {
        return usersCount;
    }

    public void prepareToStart() {
        isInGame = true;
        System.out.println("Prepare");
        for (User u : connectedUsers.values()) {
            u.prepareToStart(TIME_BEFORE_START);
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startGame();
            }
        }, TIME_BEFORE_START);
    }

    private void startGame() {
        if (!isInGame) return;
        gameRoom = new GameRoom(connectedUsers);
        System.out.println("Start");
        for (User u : connectedUsers.values()) {
            u.startGame();
        }
    }

    private void stopPrepareToStart() {
        System.out.println("Stop prepare");
        timer.cancel();
        for (User u : connectedUsers.values()) {
            u.prepareToStart(-1);
        }
    }

    public HashMap<Integer, User> getConnectedUsers() {
        return connectedUsers;
    }
}
