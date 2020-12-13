package LobbyList;


import java.util.ArrayList;
import java.util.HashMap;

public class RoomController extends Thread {
    private HashMap<Integer, Room> rooms;
    private ArrayList<User> users;
    private String serverIP;
    private ArrayList<Boolean> usedIDs;

    public RoomController(HashMap<Integer, Room> rooms, ArrayList<User> users, String serverIP) {
        this.rooms = rooms;
        this.users = users;
        this.serverIP = serverIP;
        usedIDs = new ArrayList<>();
        usedIDs.add(true);
        start();
    }

    @Override
    public void run() {
        while (true) {
            boolean isFilled = true;
            for (Room r : rooms.values()) {
                if (r.isInGame()) {
                    rooms.remove(r.getId()).removeRoom();
                }
                if (r.getUsersCount() < r.getMAX_COUNT_OF_USERS()) {
                    isFilled = false;
                }
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
                rooms.put(freeID, new Room("Room " + (rooms.size() + 1), serverIP, freeID, users));
            }
        }
    }
}
