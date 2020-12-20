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
        this.users = new HashMap<>();
        for (LobbyList.User u : users.values()) {
            this.users.put(u.getUserId(), new User(u));
        }
        generateItems();
        generateSpawnPoints();
        sender = new Sender(this.users);
        for (User u : this.users.values()) {
            Vector2 pos = getPosition();
            u.setStats(pos, 100, items);
        }
        startGame();
    }

    private void startGame(){
        for (User u : users.values()) {
            u.startGame(users);
        }
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
        Vector2 positions[] = new Vector2[]{
                new Vector2(1583, 590),
                new Vector2(528, 65),
                new Vector2(1384, 1624),
                new Vector2(1542, 1573),
                new Vector2(1641, 1131),
                new Vector2(765, 843),
                new Vector2(975, 898),
                new Vector2(908, 1378),
                new Vector2(981, 1613),
                new Vector2(668, 1509),
                new Vector2(54, 977),
                new Vector2(90, 671),
                new Vector2(1115, 241),
                new Vector2(645, 1255),
                new Vector2(192, 1127),
                new Vector2(587, 1065),
                new Vector2(599, 744),
                new Vector2(1554, 811),
                new Vector2(877, 1185),
                new Vector2(1079, 1254),
                new Vector2(1374, 990),
                new Vector2(1473, 1247),
                new Vector2(1420, 1199),
                new Vector2(1259, 1412),
                new Vector2(1185, 1488),
                new Vector2(1141, 1409),
                new Vector2(490, 1628),
                new Vector2(133, 1632),
                new Vector2(212, 1634),
                new Vector2(202, 1554),
                new Vector2(354, 1426),
                new Vector2(367, 1326),
                new Vector2(273, 1327),
                new Vector2(390, 988),
                new Vector2(422, 925),
                new Vector2(401, 875),
                new Vector2(327, 813),
                new Vector2(265, 830),
                new Vector2(212, 809),
                new Vector2(423, 753),
                new Vector2(401, 717),
                new Vector2(426, 664),
                new Vector2(389, 640),
                new Vector2(873, 1053),
                new Vector2(993, 1019),
                new Vector2(1080, 1048),
                new Vector2(1224, 421),
                new Vector2(1066, 583),
                new Vector2(1109, 627),
                new Vector2(848, 486),
                new Vector2(1096, 730),
                new Vector2(915, 656),
                new Vector2(814, 654),
                new Vector2(807, 564),
                new Vector2(1280, 641),
                new Vector2(1230, 626),
                new Vector2(1174, 586),
                new Vector2(1569, 374),
                new Vector2(1504, 356),
                new Vector2(1590, 122),
                new Vector2(1293, 114),
                new Vector2(1397, 164),
                new Vector2(1382, 69),
                new Vector2(1020, 488),
                new Vector2(973, 382),
                new Vector2(909, 342),
                new Vector2(663, 390),
                new Vector2(708, 97),
                new Vector2(194, 461),
                new Vector2(387, 339),
                new Vector2(89, 92),
                new Vector2(313, 169),
                new Vector2(313, 111),
                new Vector2(171, 143)};
        for (int i = 0; i < positions.length; i++) {
            items.add(new Item(positions[i], getRandomId(), i));
        }
    }

    private int getRandomId() {
        return (int) (Math.random() * 8349) % 5 + 1;
    }
}
