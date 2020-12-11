package Old.NewLobby;

import java.util.ArrayList;

public class RoomsController extends Thread {

    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private String serverIP;
    private ArrayList<Boolean> usedIDs;

    public RoomsController(ArrayList<Room> rooms, ArrayList<User> users, String serverIP) {
        this.rooms = rooms;
        this.users = users;
        this.serverIP = serverIP;
        usedIDs = new ArrayList<Boolean>();
        usedIDs.add(true);
        start();
    }

    @Override
    public void run() {
        while (true) {
            boolean isFilled = true;
            for (Room r : rooms) {
                if (r.isFinish()) {
                    rooms.remove(r);
                }
                if (r.getPlayersCount() < r.getMaxPlayersCount()) {
                    isFilled = false;
                }
                if (r.isUpdated()) {
                    for (User u : users) {
                        if (u.getIdInConnectedRoom() == -1)
                            u.updateRoomInfo(r);
                    }
                }
            }
            if (isFilled) {
                int freeID = -1;
                for (int i = 0; i < usedIDs.size(); i++) {
                    if (!usedIDs.get(i)) {
                        freeID = i + 1;
                        usedIDs.set(i, true);
                    }
                }
                if (freeID == -1) {
                    usedIDs.add(true);
                    freeID = usedIDs.size();
                }
                rooms.add(new Room("Room " + (rooms.size() + 1), serverIP, freeID));
            }
        }
    }
}
