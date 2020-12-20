package ru.itlab.lasthero;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import ru.itlab.lasthero.GameServer.GameScreen;
import ru.itlab.lasthero.GameServer.Utils.Controller;
import ru.itlab.lasthero.LobbyServer.Draw.LobbyListScreen.LobbyListScreen;
import ru.itlab.lasthero.LobbyServer.Draw.LobbyScreen.LobbyScreen;
import ru.itlab.lasthero.ServerSide.Connector;
import ru.itlab.lasthero.Menu.MenuScreen;

public class MainActivity extends Game {

    public final MenuScreen menuScreen;
    public final LobbyListScreen lobbyListScreen;
    public final LobbyScreen lobbyScreen;
    public final GameScreen gameScreen;

    private boolean changeScreen;
    private Screen screenToChange;

    public MainActivity(Controller.ModuleID moduleID) {
        menuScreen = new MenuScreen(this);
        lobbyListScreen = new LobbyListScreen();
        lobbyScreen = new LobbyScreen();
        gameScreen = new GameScreen(moduleID);

        Connector.here.init(this);
        changeScreen = false;
    }

    @Override
    public void create() {
        setScreen(menuScreen);
    }

    @Override
    public void render() {
        super.render();
        if (changeScreen) {
            setScreen(screenToChange);
            changeScreen = false;
        }
    }

    public void setChangeScreen(Screen screenToChange) {
        this.screenToChange = screenToChange;
        changeScreen = true;
    }

    @Override
    public void setScreen(Screen screen) {
        Screen lastScreen = getScreen();
        super.setScreen(screen);
        if (lastScreen != null)
            lastScreen.dispose();
    }
}
