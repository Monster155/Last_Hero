package Lobby;


import Lobby.Room;
import Server.Protocol;
import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class User extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Room connectedRoom;
    private int id;
    private String ip;
    private String name;

    public User(Socket socket){
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            ip = socket.getInetAddress().getHostAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    @Override
    public void run() {

    }

    public void setId(int id) {
        this.id = id;
    }

    private void connectToRoom(int id){

    }

    private void disconnectFromRoom(){
        connectedRoom.disconnectUser(this);
    }

    public void roomUpd(int id, int userCounts){

    }

    public void roomUpd(ArrayList<Pair<Boolean, User>> usersToUpd){

    }

}
