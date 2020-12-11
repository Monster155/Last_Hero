package Old.Lobby;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class LobbyConnectedUsersInput extends Thread {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ConnectedUser user;

    private LobbyConnectedUsersOutput usersOut;

    public LobbyConnectedUsersInput(Socket socket, int id, LobbyConnectedUsersOutput usersOut,
                                    ArrayList<LobbyConnectedUsersInput> usersIn) throws IOException {
        this.usersOut = usersOut;
        System.out.println("User connected!");
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        user = new ConnectedUser("Player " + id, socket.getInetAddress().getHostAddress());
        System.out.println(user.getConnectedUser());
        usersOut.sendInfoSelf(out, usersIn);
        start();
    }

    @Override
    public void run() {
        usersOut.sendInfoBroad(user);
/*try {
            String data = "";
            while (true) {
                data = (String) in.readObject();
                System.out.println(data);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public ConnectedUser getUser() {
        return user;
    }
}
