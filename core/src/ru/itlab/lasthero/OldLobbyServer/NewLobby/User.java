package ru.itlab.lasthero.OldLobbyServer.NewLobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import ru.itlab.lasthero.Protocol;

public class User extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String name;
    private String ip;
    private Room connectedRoom;
    private int idInConnectedRoom;
    private HashMap<Integer, Room> rooms;
    private boolean isConnected;

    public User(Socket socket, HashMap<Integer, Room> rooms) {
        this.socket = socket;
        this.rooms = rooms;
        isConnected = false;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            // name sets when player enter room (and connectedRoom too)
            ip = socket.getInetAddress().getHostAddress();
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
        System.out.println("User starts");
        start();
    }

    @Override
    public void run() {
        try {
            String data;
            while (true) {
                data = (String) in.readObject();
                System.out.println("Data: " + data);
                int command = Integer.parseInt(data.substring(0, 2));
                data = data.substring(2);
                switch (command) {
                    case 1:
                        //add new room to list
                        Room r = Room.decompressRoomInfo(data);
                        rooms.put(r.getRoomId(), r);
                        break;
                    case 2:
                        //update room info
                        if (isConnected) {
                            connectedRoom.updateConnectedRoom(data);
                        } else {
                            Integer id = Integer.parseInt(data.split(Protocol.DIVIDER)[0]);
                            rooms.get(id).updateRoom(data);
                        }
                        break;
                    case 3:
                        //is connected to room answer
                        String info[] = data.split(Protocol.DIVIDER);
                        if (!(isConnected = info[0].equals("1"))) {
                            connectedRoom = null;
                        } else {
                            idInConnectedRoom = Integer.parseInt(info[1]);
                            out.writeObject("02" + name);
                            out.flush();
                            System.out.println("02" + name);
                        }
                        break;
                }
                //01 - get info about lobby
                //02 - update lobby info
                //03 - is connected to room, if true - sends name
                // - start game (connect to new server (change port))
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connectToRoom(Room room) {
        try {
            out.writeObject("01" + room.getRoomId());
            System.out.println("01" + room.getRoomId());
            out.flush();
            connectedRoom = room;
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    public void setUserName(String name) {
        this.name = name;
    }
}
