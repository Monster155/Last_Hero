package ru.itlab.lasthero;

import com.badlogic.gdx.Game;

import ru.itlab.lasthero.Lobby.LobbyScreen;

public class MainActivity extends Game {

    private BattleScreen battleScreen;
    private LobbyScreen lobbyScreen;

    public MainActivity(Controller.ModuleID moduleID) {
        battleScreen = new BattleScreen(moduleID);
        lobbyScreen = new LobbyScreen();
    }

    @Override
    public void create() {
        setScreen(lobbyScreen);
    }

}
