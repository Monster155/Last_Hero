package ru.itlab.lasthero.LobbyServer.ServerSide;

import ru.itlab.lasthero.LobbyServer.Draw.LobbyScreen.LobbyScreen;
import ru.itlab.lasthero.MainActivity;
import ru.itlab.lasthero.Protocol;

public class RoomInfo extends InfoParent {

    private LobbyScreen ls;
    private MainActivity ma;
    private Room room;

    public RoomInfo(MainActivity ma) {
        this.ma = ma;
        ls = ma.lobbyScreen;
    }

    private void addNewUser(String[] data) {
        User user = new User(data[0], data[1], Integer.parseInt(data[2]));
        ls.addNewUser(user);
        room.addUser(user.getUserId(), user);
    }

    private void removeUser(int id) {
        room.removeUser(id);
    }

    private void prepareToStart(int timeBeforeStart) {
        if (timeBeforeStart > 0) {
            ls.setTimer(timeBeforeStart);
        } else {
            ls.stopTimer();
        }
    }

    private void startGame(int port) {
        //TODO start game (change screen and send port)
    }

    @Override
    public void decodeMessage(String message) {
        System.out.println("Room : " + message);
        int command = Integer.parseInt(message.substring(0, 2));
        String[] data = message.substring(2).split(Protocol.DIVIDER);
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
                startGame(Integer.parseInt(data[0]));
                break;
        }
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
