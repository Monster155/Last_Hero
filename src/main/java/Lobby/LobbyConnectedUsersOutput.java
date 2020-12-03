package Lobby;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.ArrayList;

public class LobbyConnectedUsersOutput {

    private ArrayList<LobbyConnectedUsersInput> usersIn;

    public LobbyConnectedUsersOutput(ArrayList<LobbyConnectedUsersInput> usersIn) {
        this.usersIn = usersIn;
    }

    public void sendInfoBroad(ConnectedUser user) {
        try {
            for (LobbyConnectedUsersInput userIn : usersIn) {
                userIn.getOut().writeObject("01"+user.getConnectedUser());
                userIn.getOut().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfoSelf(ObjectOutput out, ArrayList<LobbyConnectedUsersInput> usersIn){
        try {
            for (LobbyConnectedUsersInput userIn : usersIn) {
                out.writeObject("01"+userIn.getUser().getConnectedUser());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sayStartGame(){
        try {
            for (LobbyConnectedUsersInput userIn : usersIn) {
                userIn.getOut().writeObject("02");
                userIn.getOut().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
