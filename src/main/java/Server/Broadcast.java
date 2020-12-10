package Server;

import java.util.ArrayList;

public class Broadcast {
    private ArrayList<User> users;

    public Broadcast(ArrayList<User> users){
        this.users = users;
    }

    public void sendInfo(String str){
        for (User u: users) {
            u.sendInfo(str);
        }
    }

}
