package ru.itlab.lasthero.ServerSide.Infos;

import java.util.HashMap;

import ru.itlab.lasthero.LobbyServer.Draw.LobbyListScreen.LobbyListScreen;
import ru.itlab.lasthero.ServerSide.Connector;
import ru.itlab.lasthero.ServerSide.Room;
import ru.itlab.lasthero.MainActivity;

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
        int i = 0;
        Room room = new Room(data[i++], data[i++], Integer.parseInt(data[i++]), Integer.parseInt(data[i++]), Integer.parseInt(data[i++]));
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
            Connector.here.setInfo(Connector.Style.ROOM);
            ma.setChangeScreen(ma.lobbyScreen);
        } else {
            // show dialog window "Room is full"
            lls.setShowDialog();
        }
    }

    @Override
    public void executeCommand(int command, String data[]) {
        // fill lobbies array and creates new Actors
        // or remove old lobbies from array and dispose Actors
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
