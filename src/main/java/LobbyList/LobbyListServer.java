package LobbyList;


import Lobby.Room;
import Lobby.User;
import Server.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class LobbyListServer {
    public static void main(String[] args) {
        ArrayList<Room> rooms = new ArrayList<Room>();
        ArrayList<User> users = new ArrayList<User>();
        RoomsController roomsController;
        try {
            System.out.println("Old.Lobby server starts...");
            ServerSocket server = new ServerSocket(Protocol.PORT);
            rooms.add(new Room("Room 1", server.getInetAddress().getHostAddress(), 1));
            roomsController = new RoomsController(rooms, users, server.getInetAddress().getHostAddress());
            while (true) {
                Old.NewLobby.User user = new User(server.accept(), users, rooms);
                user.showRooms(rooms);
                users.add(user);
                System.out.println("Users count: " + users.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
