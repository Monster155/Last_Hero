package ru.itlab.lasthero;

import com.badlogic.gdx.Game;

public class MainActivity extends Game {

    private BattleScreen battleScreen;

    public MainActivity(Controller.ModuleID moduleID) {
        battleScreen = new BattleScreen(moduleID);
    }

    @Override
    public void create() {
        setScreen(battleScreen);
    }

}
