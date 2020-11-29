package ru.itlab.lasthero.Lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class NewUsersConnector extends Thread {

    private ObjectInputStream in;

    public NewUsersConnector(Socket socket) throws IOException {
        in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                in.readObject();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
