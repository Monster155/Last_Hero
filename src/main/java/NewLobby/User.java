package NewLobby;

import Server.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class User extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String name;
    private String ip;
    private Room connectedRoom;
    private int idInConnectedRoom;

    private ArrayList<User> users;
    private ArrayList<Room> rooms;

    public User(Socket socket, ArrayList<User> users, ArrayList<Room> rooms) {
        this.socket = socket;
        this.users = users;
        this.rooms = rooms;
        idInConnectedRoom = -1;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            // name sets when player enter room (and connectedRoom too)
            ip = socket.getInetAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }



    @Override
    public void run() {
        try {
            String data;
            while (true) {
                data = (String) in.readObject();
                int command = Integer.parseInt(data.substring(0, 2));
                data = data.substring(2);
                switch (command) {
                    // connect to lobby
                    case 1:
                        int roomId = Integer.parseInt(data);
                        for (Room r : rooms) {
                            if (r.getRoomId() == roomId) {
                                if (r.addNewUser(this)) {
                                    connectedRoom = r;
                                    System.out.println(2);
                                    out.writeObject("03" + "1" + Protocol.DIVIDER + idInConnectedRoom);// 03 is code, 1 is true answer
                                    System.out.println("03" + "1" + Protocol.DIVIDER + idInConnectedRoom);
                                } else {
                                    out.writeObject("03" + "2");// 03 is code, 2 is false answer
                                    System.out.println("03" + "2");
                                }
                                out.flush();
                                break;
                            }
                        }
                        break;
                    // get info about user
                    case 2:
                        name = data;
                        out.writeObject("02" + connectedRoom.getConnectedUsersInfo());
                        out.flush();
                        break;
                }
            }
        } catch (SocketException e) {
            users.remove(this);
            connectedRoom.disconnectUser(this);
            System.out.println("Removed");
            interrupt();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    public void showRooms(ArrayList<Room> rooms) {
        try {
            for (Room r : rooms) {
                //command sends without divider
                out.writeObject("01" + r.compressRoomInfo());
                System.out.println("01" + r.compressRoomInfo());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateRoomInfo(Room room) {
        try {
            //command sends without divider
            out.writeObject("02" + room.compressRoomInfo());
            System.out.println("02" + room.compressRoomInfo());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateConnectedRoomInfo(boolean isNew, User user, int playersCount) {
        try {
            if (isNew) {
                //command sends without divider
                out.writeObject("02" + playersCount + Protocol.DIVIDER + "1" + Protocol.DIVIDER + user.compressInfo());
                System.out.println("02" + playersCount + Protocol.DIVIDER + "1" + Protocol.DIVIDER + user.compressInfo());
            } else {
                out.writeObject("02" + playersCount + Protocol.DIVIDER + "2" + Protocol.DIVIDER + user.getIdInConnectedRoom());
                System.out.println("02" + playersCount + Protocol.DIVIDER + "2" + Protocol.DIVIDER + user.getIdInConnectedRoom());
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String compressInfo() {
        return name + Protocol.DIVIDER + ip + Protocol.DIVIDER + idInConnectedRoom;
    }

    public int getIdInConnectedRoom() {
        return idInConnectedRoom;
    }

    public void setIdInConnectedRoom(int idInConnectedRoom) {
        this.idInConnectedRoom = idInConnectedRoom;
        if (idInConnectedRoom == -1)
            connectedRoom = null;
    }

}
