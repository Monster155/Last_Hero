package Old.Lobby;

import Server.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class LobbyServer {

    private int maxPlayersInLobby;

    public static void main(String[] args) {
        ArrayList<LobbyConnectedUsersInput> usersIn = new ArrayList<LobbyConnectedUsersInput>();
        LobbyConnectedUsersOutput usersOut = new LobbyConnectedUsersOutput(usersIn);
        try {
            System.out.println("Old.Lobby server starts...");
            ServerSocket server = new ServerSocket(Protocol.PORT);
            while (true) {
                usersIn.add(new LobbyConnectedUsersInput(server.accept(), usersIn.size(), usersOut, usersIn));
                if(usersIn.size() == 3){
                    usersOut.sayStartGame();
                } else if(usersIn.size() > 3){
                    System.out.println("Error: players count > 3");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO change max count of Users in lobby to 10
}