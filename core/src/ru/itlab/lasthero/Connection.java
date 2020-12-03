package ru.itlab.lasthero;

import com.badlogic.gdx.math.Vector2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import ru.itlab.lasthero.Player.Player;

public class Connection extends Thread {

    private Player player;

    public Connection(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        System.out.println("Client starts...");
        try {
            Socket socket = new Socket(Protocol.IP_ADDRESS, Protocol.PORT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Client ready");
            Vector2 pos;
            Message message;
            while (true) {
                pos = player.getPos();
                message = new Message(pos.x, pos.y);
                System.out.println(pos);
                out.writeObject(message.getMessageToSend());
                out.flush();

                Thread.sleep(1000);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage() + " : " + e.getCause());
        }
    }
}
