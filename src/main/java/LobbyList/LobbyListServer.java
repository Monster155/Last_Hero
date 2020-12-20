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
        FXRoomController fxRoomController = new FXRoomController();
        try {
            System.out.println("Lobby server starts...");
            ServerSocket server = new ServerSocket(Protocol.PORT);
            int createRooms = 3;
            for (int i = 0; i < createRooms; i++) {
                rooms.put(i, new Room("Room " + (i + 1), server.getInetAddress().getHostAddress(), i, users));
                fxRoomController.addRoom(i, rooms.get(i));
            }
            roomController = new RoomController(rooms, users, server.getInetAddress().getHostAddress(), createRooms, fxRoomController);
            ShowLobbyList showLobbyList = new ShowLobbyList(fxRoomController);
            new Thread(showLobbyList).start();
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
