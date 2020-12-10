package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket serverSocket;
    private static ArrayList<User> users;

    public static void main(String[] args) {
        try {
            System.out.println("Server. Server is trying to start..");
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
            System.out.println("Server was closed..");
        }
    }

}


/* public static void main(String[] args) {
        try {
            System.out.println("Server.Server starts...");
            ServerSocket server = new ServerSocket(Protocol.PORT);
            Socket socket = server.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("User connected!");
            String data = "";
            while (true) {
                data = (String) in.readObject();
                Message message = Message.setSentMessage(data);
                System.out.println(message.getX() + " " + message.getY());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
 */