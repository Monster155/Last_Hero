package ru.itlab.lasthero.ServerSide.Infos;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

import ru.itlab.lasthero.GameServer.GameObjects.Item;
import ru.itlab.lasthero.GameServer.GameScreen;
import ru.itlab.lasthero.GameServer.Players.Enemy;
import ru.itlab.lasthero.GameServer.Players.Player;
import ru.itlab.lasthero.MainActivity;

public class GameInfo extends InfoParent {

    private GameScreen gameScreen;
    private MainActivity ma;
    private HashMap<Integer, Enemy> enemies;
    private HashMap<Integer, Item> items;

    public GameInfo(MainActivity ma) {
        this.ma = ma;
        this.gameScreen = ma.gameScreen;
        items = new HashMap<>();
        enemies = new HashMap<>();
    }

    private void personal(String[] data) {
        int i = 0;
        gameScreen.setPlayer(new Player(
                Integer.parseInt(data[i++]),
                new Vector2(Float.parseFloat(data[i++]), Float.parseFloat(data[i++])),
                Integer.parseInt(data[i++])));
        while (i < data.length) {
            Item item = new Item(new Vector2(Float.parseFloat(data[i++]), Float.parseFloat(data[i++])),
                    Integer.parseInt(data[i++]), Integer.parseInt(data[i++]));
            System.out.println(item);
            items.put(item.getId(), item);
        }
        gameScreen.addItems(items);
        ma.setChangeScreen(ma.gameScreen);
    }

    private void getEnemies(String data[]) {
        int i = 0;
        while (i < data.length) {
            Enemy enemy = new Enemy(new Vector2(Float.parseFloat(data[i++]), Float.parseFloat(data[i++])),
                    Integer.parseInt(data[i++]), Integer.parseInt(data[i++]));
            System.out.println(enemy);
            enemies.put(enemy.getId(), enemy);
        }
        gameScreen.addEnemies(enemies);
    }

    private void posAndDir(String data[]) {
        int i = 0;
        //id posX posY dirX dirY
        int id = Integer.parseInt(data[i++]);
        Vector2 pos = new Vector2(Float.parseFloat(data[i++]), Float.parseFloat(data[i++]));
        Vector2 dir = new Vector2(Float.parseFloat(data[i++]), Float.parseFloat(data[i++]));
        System.out.println(id + " pos:" + pos + " dir:" + dir);
        Enemy enemy = enemies.get(id);
        if (enemy != null)
            enemy.updatePosAndDir(pos, dir);
    }

    @Override
    public void executeCommand(int command, String[] data) {
        System.out.println("Game");
        switch (command) {
            // personal
            case 0:
                personal(data);
                break;
            // position and direction
            case 1:
                posAndDir(data);
                break;
            // hp
            case 2:
                break;
            // shoot
            case 3:
                break;
            // pick
            case 4:
                break;
            // hands
            case 5:
                break;
            case 6:
                getEnemies(data);
                break;
            // start game
            case 10:
                gameScreen.startGame();
                break;
        }
    }
}
