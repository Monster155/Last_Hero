package LobbyList;


import Server.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class User extends Thread {
    private final HashMap<Integer, Room> rooms;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Room connectedRoom;
    private int userId;
    private String ip;
    private String userName;

    private ArrayList<User> users;

    public User(Socket socket, HashMap<Integer, Room> rooms, ArrayList<User> users) {
        this.rooms = rooms;
        this.users = users;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            ip = socket.getInetAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showRooms();
        start();
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public void run() {
        try {
            String data;
            while (true) {
                data = (String) in.readObject();
                int command = Integer.parseInt(data.substring(0, 2));
                String[] info = data.substring(2).split(Protocol.DIVIDER);
                switch (command) {
                    // connect to Room
                    case 1:
                        connectToRoom(Integer.parseInt(info[0]), info[1]);
                        break;
                    // disconnect from Room
                    case 2:
                        disconnectFromRoom();
                        break;
                }
            }
        } catch (SocketException e) {
            disconnectFromServer();
            interrupt();
            System.out.println("Disconnected " + isInterrupted());
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }

    }

    public void setId(int id) {
        this.userId = id;
    }

    private void connectToRoom(int id, String name) {
        setUserName(name);
        if (rooms.get(id).connectUser(this)) {
            sendMessage("04" + id);
            connectedRoom = rooms.get(id);
            connectedRoom.showUsers(this);
            System.out.println("User with id " + userId + " connected to room");
            if (connectedRoom.getUsersCount() == connectedRoom.getMAX_COUNT_OF_USERS())
                connectedRoom.prepareToStart();
        } else {
            sendMessage("04" + "-1");
        }
    }

    private void disconnectFromRoom() {
        connectedRoom.disconnectUser(this);
    }

    private void disconnectFromServer() {
        if (connectedRoom != null)
            disconnectFromRoom();
        users.remove(this);
    }

    public void roomUpd(int id, int userCounts) {
        sendMessage("03" + id + Protocol.DIVIDER + userCounts);
    }

    public void roomUpd(User user, Boolean isNew) {
        if (isNew) {
            sendMessage("01" + user.getUserName() + Protocol.DIVIDER + user.getIp() + Protocol.DIVIDER + user.getUserId());
        } else {
            sendMessage("02" + user.getUserId());
        }
    }

    private void showRooms() {
        for (Room r : rooms.values()) {
            acceptNewRoom(r);
        }
    }

    public void acceptNewRoom(Room room) {
        sendMessage("01" + room.getName() + Protocol.DIVIDER + room.getIp() + Protocol.DIVIDER + room.getId());
    }

    public void removeRoom(Room room) {
        sendMessage("02" + room.getId());
    }

    public void prepareToStart(int timeBeforeStart) {
        sendMessage("03" + timeBeforeStart);
    }

    public void startGame() {
        sendMessage("04" + Protocol.GAME_PORT);
    }

    private boolean sendMessage(String message) {
        // 01 - (room update) add new user (name, ip, id)
        // 02 - (room update) remove user (id)
        // 03 - (room update) prepare to start (time before start)
        // 04 - (room update) start game (game port)

        // 01 - accept new room (name, ip, id)
        // 02 - remove room (id)
        // 03 - update users count in room (id, UC)
        // 04 - is connected to room (true/false)
        try {
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public String getIp() {
        return ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }
}
