package ru.itlab.lasthero.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.itlab.lasthero.GameServer.Utils.Controller;
import ru.itlab.lasthero.MainActivity;

import static ru.itlab.lasthero.GameServer.Utils.GamePreferences.BASE_SCREEN_SIZE;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) BASE_SCREEN_SIZE.x;
        config.height = (int) BASE_SCREEN_SIZE.y;
        new LwjglApplication(new MainActivity(Controller.ModuleID.DESKTOP), config);
    }
}
