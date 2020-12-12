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
        try {
            if (rooms.get(id).connectUser(this)) {
                out.writeObject("04" + true);
                connectedRoom = rooms.get(id);
                setUserName(name);
                System.out.println("User with id:" + userId + "connected to room");
            } else {
                out.writeObject("04" + false);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }

    }

    private void disconnectFromRoom() {
        connectedRoom.disconnectUser(this);
    }

    public void roomUpd(int id, int userCounts) {
        try {
            out.writeObject("03" + id + Protocol.DIVIDER + userCounts);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    public void roomUpd(User user, Boolean isNew) {
        try {
            if (isNew) {
                out.writeObject("01" + user.getName() + Protocol.DIVIDER + user.getIp() + Protocol.DIVIDER + user.getUserId());
            } else {
                out.writeObject("02" + user.getUserId());
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    private void showRooms() {
        for (Room r : rooms.values()) {
            acceptNewRoom(r);
        }
    }

    public void acceptNewRoom(Room room) {
        try {
            out.writeObject("01" + room.getName() + Protocol.DIVIDER + room.getIp() + Protocol.DIVIDER + room.getId());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeRoom(Room room) {
        try {
            out.writeObject("02" + room.getId());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareToStart(int timeBeforeStart) {
        try {
            out.writeObject("03" + timeBeforeStart);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    public void startGame() {
        try {
            out.writeObject("04" + Protocol.GAME_PORT);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }


    public void setUserName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

}
