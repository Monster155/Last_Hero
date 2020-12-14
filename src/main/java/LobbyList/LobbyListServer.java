package LobbyList;


import Server.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyListServer {
    public static void main(String[] args) {
        HashMap<Integer, Room> rooms = new HashMap<>();
        ArrayList<User> users = new ArrayList<>();
        RoomController roomController;
        try {
            System.out.println("Lobby server starts...");
            ServerSocket server = new ServerSocket(Protocol.PORT);
            int createRooms = 3;
            for (int i = 1; i <= createRooms; i++) {
                rooms.put(i, new Room("Room " + i, server.getInetAddress().getHostAddress(), i, users));
            }
            roomController = new RoomController(rooms, users, server.getInetAddress().getHostAddress(), createRooms);
            while (true) {
                User user = new User(server.accept(), rooms, users);
                users.add(user);
                System.out.println("Users count: " + users.size());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }
}
