package ru.itlab.lasthero;

import com.badlogic.gdx.Game;

import ru.itlab.lasthero.Lobby.LobbyScreen;
import ru.itlab.lasthero.Menu.MenuScreen;
import ru.itlab.lasthero.NewLobby.LobbyListScreen;
import ru.itlab.lasthero.NewLobby.User;

public class MainActivity extends Game {

    public final BattleScreen battleScreen;
    public final LobbyScreen lobbyScreen;
    public final MenuScreen menuScreen;
    public final LobbyListScreen lobbyListScreen;

    public MainActivity(Controller.ModuleID moduleID) {
        battleScreen = new BattleScreen(moduleID);
        lobbyScreen = new LobbyScreen(this);
        menuScreen = new MenuScreen(this);
        lobbyListScreen = new LobbyListScreen(this);
    }

    @Override
    public void create() {
        setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
        if (GamePreferences.canStartGame) {
            // TODO set listener
            setScreen(battleScreen);
            GamePreferences.canStartGame = false;
        }
    }
}
