package LobbyList;

import java.util.ArrayList;
import java.util.HashMap;

public class FXRoomController {
    private ArrayList<Room> listOfRooms = new ArrayList<Room>();

    public ArrayList<Room> getListOfRooms() {
        return listOfRooms;
    }

    public void setListOfRooms(HashMap<Integer, Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            listOfRooms.add(i, rooms.get(i));
        }
    }

    public void addRoom(Room room) {
        listOfRooms.add(room);
    }

    public void removeRoom(Room room) {
        listOfRooms.remove(room);
    }

    public int size() {
        return listOfRooms.size();
    }

    public Room getRoom(int i) {
        return listOfRooms.get(i);
    }
}
