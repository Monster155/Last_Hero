package LobbyList;


import Server.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

public class LobbyListServer {
    public static void main(String[] args) {
        HashMap<Integer, Room> rooms = new HashMap<Integer, Room>();
        ArrayList<User> users = new ArrayList<>();
        RoomController roomController;
        try {
            System.out.println("Lobby server starts...");
            ServerSocket server = new ServerSocket(Protocol.PORT);
            rooms.put(1, new Room("Room 1", server.getInetAddress().getHostAddress(), 1, users));
            roomController = new RoomController(rooms, users, server.getInetAddress().getHostAddress());
            while (true) {
                User user = new User(server.accept(), rooms);
                users.add(user);
                System.out.println("Users count: " + users.size());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }
}
