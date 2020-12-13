package LobbyList;


import Server.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class User extends Thread {
    private final HashMap<Integer, Room> rooms;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Room connectedRoom;
    private int userId;
    private String ip;
    private String name;


    public User(Socket socket, HashMap<Integer, Room> rooms) {
        this.rooms = rooms;
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
                    //
                    case 2:

                        break;
                }
            }
        } catch (SocketException e) {
            disconnectFromRoom();
            System.out.println("Disconnected");
            interrupt();
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
        if (rooms.get(id).connectUser(this)) {
            sendMessage("04" + true);
            connectedRoom = rooms.get(id);
            setUserName(name);
            System.out.println("User with id:" + userId + "connected to room");
        } else {
            sendMessage("04" + false);
        }
    }

    private void disconnectFromRoom() {
        connectedRoom.disconnectUser(this);
    }

    public void roomUpd(int id, int userCounts) {
        sendMessage("03" + id + Protocol.DIVIDER + userCounts);
    }

    public void roomUpd(User user, Boolean isNew) {
        if (isNew) {
            sendMessage("01" + user.getName() + Protocol.DIVIDER + user.getIp() + Protocol.DIVIDER + user.getUserId());
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
        // 01 - (room update) add new user
        // 02 - (room update) remove user
        // 03 - (room update) update users count in room
        // 04 - (room update) is connected to room (true/false)

        // 01 - accept new room
        // 02 - remove room
        // 03 - prepare to start
        // 04 - start game
        try {
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

}
