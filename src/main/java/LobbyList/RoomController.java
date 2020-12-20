package LobbyList;


import java.util.ArrayList;
import java.util.HashMap;

public class RoomController extends Thread {
    private HashMap<Integer, Room> rooms;
    private ArrayList<User> users;
    private String serverIP;
    private ArrayList<Boolean> usedIDs;
    private ArrayList<Integer> roomsIdToRemove;
    private FXRoomController fxRoomController;

    public RoomController(HashMap<Integer, Room> rooms, ArrayList<User> users, String serverIP, int createRooms) {
        this.rooms = rooms;
        this.users = users;
        this.serverIP = serverIP;
        usedIDs = new ArrayList<>();
        roomsIdToRemove = new ArrayList<>();
        fxRoomController = new FXRoomController();
        for (int i = 0; i < createRooms; i++) {
            usedIDs.add(true);
        }
        start();
    }

    @Override
    public void run() {
        while (true) {
            boolean isFilled = true;
            for (Room r : rooms.values()) {
                if (r.isInGame()) {
                    roomsIdToRemove.add(r.getId());
                }
                if (r.getUsersCount() < r.getMAX_COUNT_OF_USERS()) {
                    isFilled = false;
                }
            }
            if (roomsIdToRemove.size() > 0) {
                int id = roomsIdToRemove.remove(0);
                fxRoomController.removeLobby(rooms.get(id));
                rooms.remove(id).removeRoom();
                usedIDs.set(id, false);
            }
            if (isFilled) {
                int freeID = -1;
                for (int i = 0; i < usedIDs.size(); i++) {
                    if (!usedIDs.get(i)) {
                        freeID = i;
                        usedIDs.set(i, true);
                    }
                }
                if (freeID == -1) {
                    freeID = usedIDs.size();
                    usedIDs.add(true);
                }
                rooms.put(freeID, new Room("Room " + (freeID + 1), serverIP, freeID, users));
                fxRoomController.addRoom(rooms.get(freeID));
            }
        }
    }
}
