package ru.itlab.lasthero.ServerSide.Infos;

import ru.itlab.lasthero.LobbyServer.Draw.LobbyScreen.LobbyScreen;
import ru.itlab.lasthero.ServerSide.Connector;
import ru.itlab.lasthero.ServerSide.Room;
import ru.itlab.lasthero.ServerSide.User;
import ru.itlab.lasthero.MainActivity;

public class RoomInfo extends InfoParent {

    private LobbyScreen ls;
    private MainActivity ma;

    public RoomInfo(MainActivity ma) {
        this.ma = ma;
        ls = ma.lobbyScreen;
    }

    private void addNewUser(String[] data) {
        User user = new User(data[0], data[1], Integer.parseInt(data[2]));
        ls.addNewUser(user);
    }

    private void removeUser(int id) {
        ls.getRoom().removeUser(id);
    }

    private void prepareToStart(int timeBeforeStart) {
        if (timeBeforeStart > 0) {
            ls.setTimer(timeBeforeStart);
        } else {
            ls.stopTimer();
        }
    }

    private void startGame() {
        Connector.here.setInfo(Connector.Style.GAME);
    }

    @Override
    public void executeCommand(int command, String data[]) {
        System.out.println("Room");
        switch (command) {
            // 01 - (room update) add new user (name, ip, id)
            case 1:
                addNewUser(data);
                break;
            // 02 - (room update) remove user (id)
            case 2:
                removeUser(Integer.parseInt(data[0]));
                break;
            // 03 - (room update) prepare to start (time before start)
            case 3:
                prepareToStart(Integer.parseInt(data[0]));
                break;
            // 04 - (room update) start game (game port)
            case 4:
                startGame();
                break;
        }
    }
}
