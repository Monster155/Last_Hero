package ru.itlab.lasthero.Lobby.NewLobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import ru.itlab.lasthero.Protocol;

public class SocketUser extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ArrayList<LobbyInfo> lobbyInfos;

    public SocketUser(ArrayList<LobbyInfo> lobbyInfos) {
        this.lobbyInfos = lobbyInfos;
        try {
            Socket socket = new Socket(Protocol.IP_ADDRESS, Protocol.PORT);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            start();
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
                    case 3:
                        LobbyInfo lobbyInfo = LobbyInfo.getLobbyInfo(data);
                        lobbyInfos.add(lobbyInfo);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }
}
