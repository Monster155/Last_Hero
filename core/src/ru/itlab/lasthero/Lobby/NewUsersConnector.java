package ru.itlab.lasthero.Lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import ru.itlab.lasthero.Protocol;

public class NewUsersConnector extends Thread {

    private ObjectInputStream in;
    private ArrayList<ConnectedUser> users;

    public NewUsersConnector(ArrayList<ConnectedUser> users) {
        this.users = users;
        try {
            Socket socket = new Socket(Protocol.IP_ADDRESS, Protocol.PORT);
            in = new ObjectInputStream(socket.getInputStream());
            // user name and ip gives from server
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {
        try {
            String data = "";
            while (true) {
                data = (String) in.readObject();
                System.out.println(data);
                ConnectedUser user = ConnectedUser.setConnectedUser(data);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
