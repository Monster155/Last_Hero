package ru.itlab.lasthero.GameServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.util.Pair;
import ru.itlab.lasthero.GameServer.Player.OtherUser;
import ru.itlab.lasthero.GameServer.Player.Player;
import ru.itlab.lasthero.Protocol;

public class Connection extends Thread {

    private Player player;
    private ArrayList<Pair<Boolean, OtherUser>> otherUsersToUpdate;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Connection(Player player, ArrayList<Pair<Boolean, OtherUser>> otherUsersToUpdate) {
        this.player = player;
        this.otherUsersToUpdate = otherUsersToUpdate;
        try {
            socket = new Socket(Protocol.IP_ADDRESS, Protocol.GAME_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Client starts...");
        try {
            String data;
            String info[];
            while (true) {
                data = (String) in.readObject();
                int command = Integer.parseInt(data.substring(0, 2));
                info = data.substring(2).split(Protocol.DIVIDER);
                int i = 0;
                switch (command) {
                    //connected user
                    case 1:
                        otherUsersToUpdate.add(new Pair<>(true, new OtherUser(info[i++], info[i++])));
                        break;
                    //disconnected user
                    case 2:
                        otherUsersToUpdate.add(new Pair<>(false, new OtherUser(info[i++], info[i++])));
                        break;
                    //user data
                    case 3:

                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void send() {
        try {
            System.out.println("Client ready");
            out.writeObject(player.getData());
            out.flush();
//                Thread.sleep(1000);
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }
}
