package Server;

import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket serverSocket;
    private static ArrayList<User> users;

    public static void main(String[] args) {
        try {
            System.out.println("Server. Server was started..");
            serverSocket = new ServerSocket(Protocol.GAME_PORT);
            users = new ArrayList<User>();
            while (true) {
                User user = new User(serverSocket.accept(), users);
                users.add(user);
                System.out.println("We have " + users.size() + " users \n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Server. Server was closed..");
        }
    }

}
