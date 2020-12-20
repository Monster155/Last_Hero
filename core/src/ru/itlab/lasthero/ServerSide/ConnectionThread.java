package ru.itlab.lasthero.ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ru.itlab.lasthero.Protocol;

public class ConnectionThread extends Thread {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean isConnected;

    public ConnectionThread() {
        isConnected = false;
        start();
    }

    @Override
    public void run() {
        while (!isConnected) {
            try {
                socket = new Socket(Protocol.IP_ADDRESS, Protocol.PORT);
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                isConnected = true;
            } catch (IOException e) {
                System.out.println(e.getMessage() + " : " + e.getCause());
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage() + " : " + e.getCause());
            }
        }
        Connector.here.setSocketAndStreams(socket, out, in);
    }

    public boolean isConnected() {
        return isConnected;
    }
}
