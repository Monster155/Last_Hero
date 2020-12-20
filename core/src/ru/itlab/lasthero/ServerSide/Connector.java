package ru.itlab.lasthero.ServerSide;

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

    private ru.itlab.lasthero.ServerSide.Infos.InfoParent usingInfo;
    private ru.itlab.lasthero.ServerSide.Infos.ServerInfo serverInfo;
    private ru.itlab.lasthero.ServerSide.Infos.RoomInfo roomInfo;
    private ru.itlab.lasthero.ServerSide.Infos.GameInfo gameInfo;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void init(MainActivity ma) {
        serverInfo = new ru.itlab.lasthero.ServerSide.Infos.ServerInfo(ma);
        roomInfo = new ru.itlab.lasthero.ServerSide.Infos.RoomInfo(ma);
        gameInfo = new ru.itlab.lasthero.ServerSide.Infos.GameInfo(ma);

        setInfo(Style.SERVER);
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

    public void ready() {
        sendMessage(GameDataSender.START_GAME);
    }

    public void connectToRoom(int id, String playerName) {
        sendMessage("01" + id + Protocol.DIVIDER + playerName);
    }

    public boolean sendMessage(String message) {
        // 01 - connect to Room (id, player name)
        // 02 - disconnect from Room
        try {
            System.out.println(message);
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
            return false;
        }
    }

    public void setInfo(Style style) {
        switch (style) {
            case SERVER:
                usingInfo = serverInfo;
                break;
            case ROOM:
                usingInfo = roomInfo;
                break;
            case GAME:
                usingInfo = gameInfo;
                sendMessage("99");
                break;
        }
    }

    public void setSocketAndStreams(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        start();
    }

    public enum Style {
        SERVER,
        ROOM,
        GAME
    }
}
