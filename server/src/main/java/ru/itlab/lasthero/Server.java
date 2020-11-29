package ru.itlab.lasthero;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Server starts...");
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
}