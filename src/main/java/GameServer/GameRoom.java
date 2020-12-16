package GameServer;

import GameServer.Utils.Item;
import GameServer.Utils.SpawnPoint;
import GameServer.Utils.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRoom {
    private HashMap<Integer, User> users;
    private ArrayList<SpawnPoint> spawnPoints;
    private ArrayList<Item> items;
    private Sender sender;

    public GameRoom(HashMap<Integer, LobbyList.User> users) {
        generateSpawnPoints();
        this.users = new HashMap<>();
        for (LobbyList.User u : users.values()) {
            this.users.put(u.getUserId(), new User(u));
        }
        sender = new Sender(this.users);
        for (User u : this.users.values()) {
            Vector2 pos = getPosition();
            u.setStats(pos.getX(), pos.getY(), 100, items);
        }
        generateItems();
    }

    private Vector2 getPosition() {
        for (SpawnPoint point : spawnPoints) {
            if (point.isFree()) {
                point.setFree(false);
                return point.getLocation();
            }
        }
        return new Vector2(0, 0);
    }

    private void generateSpawnPoints() {
        spawnPoints = new ArrayList<>();
        spawnPoints.add(new SpawnPoint(new Vector2(395, 158)));
        spawnPoints.add(new SpawnPoint(new Vector2(845, 373)));
        spawnPoints.add(new SpawnPoint(new Vector2(1214, 106)));
        spawnPoints.add(new SpawnPoint(new Vector2(1641, 417)));
        spawnPoints.add(new SpawnPoint(new Vector2(1249, 688)));
        spawnPoints.add(new SpawnPoint(new Vector2(181, 705)));
        spawnPoints.add(new SpawnPoint(new Vector2(518, 1003)));
        spawnPoints.add(new SpawnPoint(new Vector2(446, 1343)));
        spawnPoints.add(new SpawnPoint(new Vector2(1549, 1452)));
        spawnPoints.add(new SpawnPoint(new Vector2(78, 1581)));
    }

    private void generateItems() {
        items = new ArrayList<>();
        items.add(new Item(new Vector2(1583, 590), getRandomId()));
        items.add(new Item(new Vector2(528, 65), getRandomId()));
        items.add(new Item(new Vector2(1384, 1624), getRandomId()));
        items.add(new Item(new Vector2(1542, 1573), getRandomId()));
        items.add(new Item(new Vector2(1641, 1131), getRandomId()));
        items.add(new Item(new Vector2(765, 843), getRandomId()));
        items.add(new Item(new Vector2(975, 898), getRandomId()));
        items.add(new Item(new Vector2(908, 1378), getRandomId()));
        items.add(new Item(new Vector2(981, 1613), getRandomId()));
        items.add(new Item(new Vector2(668, 1509), getRandomId()));
        items.add(new Item(new Vector2(54, 977), getRandomId()));
        items.add(new Item(new Vector2(90, 671), getRandomId()));
        items.add(new Item(new Vector2(1115, 241), getRandomId()));
        items.add(new Item(new Vector2(645, 1255), getRandomId()));
        items.add(new Item(new Vector2(192, 1127), getRandomId()));
        items.add(new Item(new Vector2(587, 1065), getRandomId()));
        items.add(new Item(new Vector2(599, 744), getRandomId()));
        items.add(new Item(new Vector2(1554, 811), getRandomId()));
        items.add(new Item(new Vector2(877, 1185), getRandomId()));
        items.add(new Item(new Vector2(1079, 1254), getRandomId()));
        items.add(new Item(new Vector2(1374, 990), getRandomId()));
        items.add(new Item(new Vector2(1473, 1247), getRandomId()));
        items.add(new Item(new Vector2(1420, 1199), getRandomId()));
        items.add(new Item(new Vector2(1259, 1412), getRandomId()));
        items.add(new Item(new Vector2(1185, 1488), getRandomId()));
        items.add(new Item(new Vector2(1141, 1409), getRandomId()));
        items.add(new Item(new Vector2(490, 1628), getRandomId()));
        items.add(new Item(new Vector2(133, 1632), getRandomId()));
        items.add(new Item(new Vector2(212, 1634), getRandomId()));
        items.add(new Item(new Vector2(202, 1554), getRandomId()));
        items.add(new Item(new Vector2(354, 1426), getRandomId()));
        items.add(new Item(new Vector2(367, 1326), getRandomId()));
        items.add(new Item(new Vector2(273, 1327), getRandomId()));
        items.add(new Item(new Vector2(390, 988), getRandomId()));
        items.add(new Item(new Vector2(422, 925), getRandomId()));
        items.add(new Item(new Vector2(401, 875), getRandomId()));
        items.add(new Item(new Vector2(327, 813), getRandomId()));
        items.add(new Item(new Vector2(265, 830), getRandomId()));
        items.add(new Item(new Vector2(212, 809), getRandomId()));
        items.add(new Item(new Vector2(423, 753), getRandomId()));
        items.add(new Item(new Vector2(401, 717), getRandomId()));
        items.add(new Item(new Vector2(426, 664), getRandomId()));
        items.add(new Item(new Vector2(389, 640), getRandomId()));
        items.add(new Item(new Vector2(873, 1053), getRandomId()));
        items.add(new Item(new Vector2(993, 1019), getRandomId()));
        items.add(new Item(new Vector2(1080, 1048), getRandomId()));
        items.add(new Item(new Vector2(1224, 421), getRandomId()));
        items.add(new Item(new Vector2(1066, 583), getRandomId()));
        items.add(new Item(new Vector2(1109, 627), getRandomId()));
        items.add(new Item(new Vector2(848, 486), getRandomId()));
        items.add(new Item(new Vector2(1096, 730), getRandomId()));
        items.add(new Item(new Vector2(915, 656), getRandomId()));
        items.add(new Item(new Vector2(814, 654), getRandomId()));
        items.add(new Item(new Vector2(807, 564), getRandomId()));
        items.add(new Item(new Vector2(1280, 641), getRandomId()));
        items.add(new Item(new Vector2(1230, 626), getRandomId()));
        items.add(new Item(new Vector2(1174, 586), getRandomId()));
        items.add(new Item(new Vector2(1569, 374), getRandomId()));
        items.add(new Item(new Vector2(1504, 356), getRandomId()));
        items.add(new Item(new Vector2(1590, 122), getRandomId()));
        items.add(new Item(new Vector2(1293, 114), getRandomId()));
        items.add(new Item(new Vector2(1397, 164), getRandomId()));
        items.add(new Item(new Vector2(1382, 69), getRandomId()));
        items.add(new Item(new Vector2(1020, 488), getRandomId()));
        items.add(new Item(new Vector2(973, 382), getRandomId()));
        items.add(new Item(new Vector2(909, 342), getRandomId()));
        items.add(new Item(new Vector2(663, 390), getRandomId()));
        items.add(new Item(new Vector2(708, 97), getRandomId()));
        items.add(new Item(new Vector2(194, 461), getRandomId()));
        items.add(new Item(new Vector2(387, 339), getRandomId()));
        items.add(new Item(new Vector2(89, 92), getRandomId()));
        items.add(new Item(new Vector2(313, 169), getRandomId()));
        items.add(new Item(new Vector2(313, 111), getRandomId()));
        items.add(new Item(new Vector2(171, 143), getRandomId()));
    }

    private int getRandomId() {
        return (int) (Math.random() * 8349) % 5 + 1;
    }
}