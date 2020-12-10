package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;

public class User extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Broadcast broadcast;
    private int id;
    private ArrayList<User> users;

    public User(Socket socket, ArrayList<User> users) throws IOException {
        this.socket = socket;
        this.users = users;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        broadcast = new Broadcast(users);
        start();

    }


    @Override
    public void run() {
        try {
            String data;
            while (true){
                data = (String) in.readObject();
                broadcast.sendInfo(data);
                System.out.println(data);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }
    public void sendInfo(String str) {
        try {
            out.writeObject(str);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
