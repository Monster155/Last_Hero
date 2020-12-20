package LobbyList;

import java.util.ArrayList;

public class FXRoomController {
    private ArrayList<Room> listOfRooms = new ArrayList<Room>();

    public ArrayList<Room> getListOfRooms() {
        return listOfRooms;
    }

    public void addRoom(Room room) {
        listOfRooms.add(room);
    }

    public void removeLobby(Room room) {
        listOfRooms.remove(room);
    }

    public int size() {
        return listOfRooms.size();
    }

    public Room getRoom(int i) {
        return listOfRooms.get(i);
    }
}
