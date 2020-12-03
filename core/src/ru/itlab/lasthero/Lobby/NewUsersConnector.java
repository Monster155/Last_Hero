package ru.itlab.lasthero.Lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import ru.itlab.lasthero.GamePreferences;
import ru.itlab.lasthero.Protocol;

public class NewUsersConnector extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<ConnectedUser> users;
    private boolean hasConnection;

    public NewUsersConnector(ArrayList<ConnectedUser> users) {
        this.users = users;
        hasConnection = false;
        try {
            Socket socket = new Socket(Protocol.IP_ADDRESS, Protocol.PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            // user name and ip gives from server
            start();
            hasConnection = true;
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    @Override
    public void run() {
        try {
            String data;
            while (true) {
                data = (String) in.readObject();
                int command = Integer.parseInt(data.substring(0, 2));
                data = data.substring(2);
                System.out.println(data);
                switch (command) {
                    case 1:
                        ConnectedUser user = ConnectedUser.setConnectedUser(data);
                        users.add(user);
                        break;
                    case 2:
                        GamePreferences.canStartGame = true;
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }

    public boolean isHasConnection() {
        return hasConnection;
    }
}
