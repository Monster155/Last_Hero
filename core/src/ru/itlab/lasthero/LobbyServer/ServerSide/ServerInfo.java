package ru.itlab.lasthero.LobbyServer.ServerSide;

import java.util.HashMap;

import ru.itlab.lasthero.LobbyServer.Draw.LobbyListScreen.LobbyListScreen;
import ru.itlab.lasthero.MainActivity;
import ru.itlab.lasthero.Protocol;

public class ServerInfo extends InfoParent {

    private HashMap<Integer, Room> rooms;
    private LobbyListScreen lls;
    private MainActivity ma;

    public ServerInfo(MainActivity ma) {
        this.ma = ma;
        this.lls = ma.lobbyListScreen;
        rooms = new HashMap<>();
    }

    private void addNewRoom(String[] data) {
        Room room = new Room(data[0], data[1], Integer.parseInt(data[2]));
        lls.addNewRoom(room);
        rooms.put(room.getId(), room);
    }

    private void removeRoom(int id) {
        rooms.remove(id).getRoomActor().delete();
    }

    private void updateUsersCountInRoom(int id, int uc) {
        rooms.get(id).setUsersCount(uc);
    }

    private void isConnectedToRoom(int connectedRoomId) {
        if (connectedRoomId >= 0) {
            // change screen
            ma.lobbyScreen.setRoom(rooms.get(connectedRoomId));
            Connector.here.getRoomInfo().setRoom(rooms.get(connectedRoomId));
            Connector.here.setInfo(Connector.here.getRoomInfo());
            ma.setChangeScreen(ma.lobbyScreen);
        } else {
            // show dialog window "Room is full"
            lls.setShowDialog();
        }
    }

    @Override
    public void decodeMessage(String message) {
        // fill lobbies array and creates new Actors
        // or remove old lobbies from array and dispose Actors
        System.out.println("Server : " + message);
        int command = Integer.parseInt(message.substring(0, 2));
        String[] data = message.substring(2).split(Protocol.DIVIDER);
        switch (command) {
            // 01 - accept new room (name, ip, id)
            case 1:
                addNewRoom(data);
                break;
            // 02 - remove room (id)
            case 2:
                removeRoom(Integer.parseInt(data[0]));
                break;
            // 03 - update users count in room (id, UC)
            case 3:
                updateUsersCountInRoom(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
                break;
            // 04 - is connected to room (id/-1)
            case 4:
                isConnectedToRoom(Integer.parseInt(data[0]));
                break;
        }
    }
}
