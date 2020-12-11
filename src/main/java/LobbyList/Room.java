package LobbyList;

import javafx.util.Pair;

import java.util.ArrayList;

public class Room {
    private final int MAX_COUNT_OF_USERS = 10;
    private ArrayList<User> users;
    private int usersCount;
    private int id;
    private String name;
    private String ip;
    private ArrayList<Pair<Boolean, User>>usersToUpd;


    public Room(String name, String ip, int id) {
        this.name = name;
        this.ip = ip;
        this.id = id;
        usersCount = 0;
        users = new ArrayList<>();
        usersToUpd = new ArrayList<>();
    }


    public boolean connectUser(User user) {
        if (usersCount >= MAX_COUNT_OF_USERS){
            return false;
        }
        usersCount++;
        users.add(user);
        update(users);
        return true;
    }

    public boolean disconnectUser(User user){
        usersCount--;
        users.remove(user);
        update(users);
        return true;
    }

   public void update(ArrayList<User> users){
        //users.roomUpd();
        //users.roomUpd();
   }

}
