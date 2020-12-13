package ru.itlab.lasthero.LobbyServer.ServerSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ru.itlab.lasthero.MainActivity;
import ru.itlab.lasthero.Protocol;

public class Connector extends Thread {

    public static Connector here;

    static {
        here = new Connector();
    }

    private InfoParent usingInfo;
    private ServerInfo serverInfo;
    private RoomInfo roomInfo;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void init(MainActivity ma) {
        serverInfo = new ServerInfo(ma);
        roomInfo = new RoomInfo(ma);

        setInfo(serverInfo);
    }

    public boolean connect() {
        try {
            socket = new Socket(Protocol.IP_ADDRESS, Protocol.PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            start();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    @Override
    public void run() {
        try {
            String data;
            while (true) {
                data = (String) in.readObject();
                usingInfo.decodeMessage(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connectToRoom(int id, String playerName) {
        sendMessage("01" + id + Protocol.DIVIDER + playerName);
    }

    private boolean sendMessage(String message) {
        // 01 - connect to Room (id, player name)
        // 02 - disconnect from Room
        try {
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public void setInfo(InfoParent info) {
        usingInfo = info;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }
}
